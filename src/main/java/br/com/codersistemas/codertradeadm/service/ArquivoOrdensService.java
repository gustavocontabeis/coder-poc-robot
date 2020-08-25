package br.com.codersistemas.codertradeadm.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.codersistemas.codertradeadm.domain.ExecucaoVenda;
import br.com.codersistemas.codertradeadm.domain.Natureza;
import br.com.codersistemas.codertradeadm.domain.Ordem;
import br.com.codersistemas.codertradeadm.domain.Status;
import br.com.codersistemas.codertradeadm.domain.Tipo;
import br.com.codersistemas.codertradeadm.domain.TipoTrade;
import br.com.codersistemas.codertradeadm.repository.ExecucaoVendaRepository;
import br.com.codersistemas.codertradeadm.repository.OrdemRepository;
import br.com.codersistemas.codertradeadm.utils.DateUtils;
import br.com.codersistemas.libs.utils.LangUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArquivoOrdensService {
	
	//private static final String DIR_FILES = "/home/gustavo/trade/ordens/swing-trade";
	private static final String DIR_FILES = "/home/gustavo/trade/ordens/teste";

	@Autowired
	private OrdemRepository repository;
	
	@Autowired
	private ExecucaoVendaRepository execucaoVendaRepository;
	
	public List<Ordem> importarArquivos() {
		log.info(">>>>> importarArquivos");
		List<Ordem> linhas = new ArrayList<>();
		File dir = new File(DIR_FILES);
		File[] listFiles = dir.listFiles();
		for (File file : listFiles) {
			log.info("Arquivo: {}", file.getName());
			if (file.getName().endsWith("csv")) {
				try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
					String line;
					int nrLine = 0;
					while ((line = br.readLine()) != null) {
						if(nrLine++ == 0) {
							continue;
						}
						if(line.startsWith("F")) {
							break;
						}
						String[] values = line.split(";");
						
						if(repository.findByCodigo(new Long(values[0])) != null) {
							continue;
						}
						
						Ordem ordem = new Ordem();
						ordem.setCodigo(new Long(values[0]));
						ordem.setAtivo(values[1]);
						ordem.setTipoTrade(TipoTrade.SWING);
						ordem.setTipo(Tipo.fromDescricao(values[2]));
						ordem.setNatureza(Natureza.fromDescricao(values[3]));
						ordem.setStatus(Status.fromDescricao(values[4]));
						ordem.setPreco(LangUtil.toBigDecimal(values[5]));
						ordem.setStop(LangUtil.toBigDecimal(values[6]));
						ordem.setGain(LangUtil.toBigDecimal(values[7]));
						ordem.setQuantidade(LangUtil.toBigDecimal(values[8]));
						ordem.setQtdExecutada(LangUtil.toBigDecimal(values[9]));
						ordem.setPrecoMedio(LangUtil.toBigDecimal(values[10]));
						ordem.setCriadoEm(DateUtils.parseTimestampBR(values[11]));
						ordem.setCriadoPor(values[12]);
						ordem.setValidade(DateUtils.parseDateBR(values[13]));
						ordem.setCanceladaEm(DateUtils.parseTimestampBR(values[14]));
						ordem.setCanceladaPor(StringUtils.stripToNull(values[15]));
						ordem.setAtualizadoEm(DateUtils.parseTimestampBR(values[16]));
						ordem.setQuantidadeDisponivel(ordem.getQuantidade());
						ordem.setSaldoQuantidade(BigDecimal.ZERO);
						linhas.add(ordem);
						repository.save(ordem);
						//System.out.println(ordem);
						if(ordem.getAtivo().equals("ITSA4")||ordem.getAtivo().equals("ITSA4F")) {
							int a= 1;
						}
							
						if(ordem.getNatureza() == Natureza.VENDA) {
							executarVendas(ordem);
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return linhas;
	}

	private void executarVendas(Ordem venda) {
		String[] ativos = getNomeAtivoNormalFracionado(venda.getAtivo());
		List<Ordem> ordensCompraEmAberto = execucaoVendaRepository.buscarOrdensCompraEmAberto(ativos, Natureza.COMPRA);
		int quantidadeVenda = venda.getQuantidade().intValue();
		for (Ordem compraEmAberto : ordensCompraEmAberto) {
			int quantidadeDisponivel = compraEmAberto.getQuantidadeDisponivel().intValue();
			
			compraEmAberto.setQuantidadeDisponivel(new BigDecimal(quantidadeDisponivel - quantidadeVenda));
			repository.save(compraEmAberto);
			
			float valorTotalCompra = compraEmAberto.getPreco().floatValue() * quantidadeVenda;
			float valorTotalVenda = venda.getPreco().floatValue() * quantidadeVenda;
			float resultadoTotalVenda = valorTotalVenda - valorTotalCompra;
			
			ExecucaoVenda execucaoVenda = new ExecucaoVenda();
			execucaoVenda.setCompra(compraEmAberto);
			execucaoVenda.setValorUnitarioCompra(compraEmAberto.getPreco());
			execucaoVenda.setValorUnitarioVenda(venda.getPreco());
			execucaoVenda.setQuantidade(BigDecimal.valueOf(quantidadeVenda));
			execucaoVenda.setResultado(new BigDecimal(resultadoTotalVenda));
			execucaoVenda.setValorTotalCompra(new BigDecimal(valorTotalCompra));
			execucaoVenda.setValorTotalVenda(new BigDecimal(valorTotalVenda));
			
			execucaoVendaRepository.save(execucaoVenda);
			
			quantidadeVenda -= execucaoVenda.getQuantidade().intValue();
			if(quantidadeVenda == 0) {
				break;
			}
			
		}
		
	}

	private String[] getNomeAtivoNormalFracionado(String ativo) {
		if(ativo.length() == 5) {
			return new String[] {ativo, ativo+"F"};
		}else {
			return new String[] {ativo.substring(0, 5), ativo};
		}
	}

	public void calcular(List<Ordem> ordens) {
		ordens.sort(Comparator
                .comparing(Ordem::getAtivoReal)
                .thenComparing(Ordem::getAtualizadoEm));
		Map<String, List<Ordem>> collect = ordens.stream().collect(Collectors.groupingBy(Ordem::getAtivoReal));
		collect.entrySet().stream().map(ordensAtivo->calcularResultados(ordensAtivo)).forEach(e-> System.out.println(e));
	}

	private Object calcularResultados(Entry<String, List<Ordem>> ordensAtivo) {
		
		System.out.println(ordensAtivo.getKey()+" ----------------------------------------");
		
		List<Ordem> ordens = ordensAtivo.getValue();
		int saldoQuantidade = 0;
		for (Ordem ordem : ordens) {
			
			if(ordem.getNatureza() == Natureza.COMPRA) {
				saldoQuantidade += ordem.getQuantidade().intValue();
			}else {
				saldoQuantidade -= ordem.getQuantidade().intValue();
			}
			ordem.setSaldoQuantidade(new BigDecimal(saldoQuantidade));
			repository.save(ordem);
			
			if(ordem.getNatureza() == Natureza.VENDA) {
				venderAtivos(ordens, ordem);
			}
			
			System.out.println(ordem.print());
		}
		
		return null;
	}

	private void venderAtivos(List<Ordem> ordens, Ordem venda) {
		System.out.println("Venda: "+venda.print());
		for (Ordem ordem : ordens) {
			if(ordem.getNatureza() == Natureza.COMPRA
					&& ordem.getQuantidadeDisponivel().intValue() > 0) {
				
				if(venda.getQuantidade().intValue() < ordem.getQuantidadeDisponivel().intValue()) {
					int saldo = ordem.getQuantidadeDisponivel().intValue() - venda.getQuantidade().intValue(); 
					ordem.setQuantidadeDisponivel(BigDecimal.valueOf(saldo));
				}
				
				ExecucaoVenda execucao = new ExecucaoVenda();
				execucao.setCompra(ordem);
				if(ordem.getQuantidadeDisponivel().intValue() >= venda.getQuantidade().intValue()) {
					break;
				}
			}
		}
	}


}
