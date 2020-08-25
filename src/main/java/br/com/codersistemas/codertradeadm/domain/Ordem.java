
package br.com.codersistemas.codertradeadm.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.codersistemas.codertradeadm.utils.NumberUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ordem")
public class Ordem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seq_ordem", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_ordem", initialValue = 1000, allocationSize = 1)
	@Column(name = "id_ordem", nullable = false)
	private Long id;

	@Column(name = "codigo", nullable = false, unique=true)
	private Long codigo;

	@NotNull(message = "Ativo deve ser preenchido.")
	@Column(name = "ativo", length = 6, nullable = false)
	private String ativo;

	@NotNull(message = "Tipo Trade deve ser preenchido.")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_trade", length = 30, nullable = false)
	private TipoTrade tipoTrade;

	@NotNull(message = "Tipo deve ser preenchido.")
	@Enumerated(EnumType.STRING)
	@Column(length = 30, nullable = false)
	private Tipo tipo;

	@NotNull(message = "Natureza deve ser preenchido.")
	@Enumerated(EnumType.STRING)
	@Column(length = 30, nullable = false)
	private Natureza natureza;

	@NotNull(message = "Status deve ser preenchido.")
	@Enumerated(EnumType.STRING)
	@Column(length = 30, nullable = false)
	private Status status;

	@NotNull(message = "Preco deve ser preenchido.")
	@Column(name = "preco", precision = 10, scale = 2, nullable = false)
	private BigDecimal preco;

	@Column(name = "stop", precision = 10, scale = 2, nullable = true)
	private BigDecimal stop;

	@Column(name = "gain", precision = 10, scale = 2, nullable = true)
	private BigDecimal gain;

	@NotNull(message = "Quantidade deve ser preenchido.")
	@Column(name = "quantidade", precision = 10, scale = 0, nullable = false)
	private BigDecimal quantidade;

	@NotNull(message = "Qtd executada deve ser preenchido.")
	@Column(name = "qtd_executada", precision = 10, scale = 0, nullable = false)
	private BigDecimal qtdExecutada;

	@NotNull(message = "Preco medio deve ser preenchido.")
	@Column(name = "preco_medio", precision = 10, scale = 2, nullable = true)
	private BigDecimal precoMedio;

	@NotNull(message = "Criado em deve ser preenchido.")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "criado_em", nullable = false)
	private Date criadoEm;

	@NotNull(message = "Criado por deve ser preenchido.")
	@Column(name = "criado_por", length = 50, nullable = false)
	private String criadoPor;

	@NotNull(message = "Validade deve ser preenchido.")
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "validade", nullable = false)
	private Date validade;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cancelada_em", length = 255, nullable = true)
	private Date canceladaEm;

	@Column(name = "cancelada_por", length = 255, nullable = true)
	private String canceladaPor;

	@NotNull(message = "Atualizado em deve ser preenchido.")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "atualizado_em", length = 255, nullable = false)
	private Date atualizadoEm;
	
	@NotNull(message = "Quantidade Disponível deve ser preenchido.")
	@Column(name = "quantidade_disponivel", precision = 10, scale = 0, nullable = false)
	private BigDecimal quantidadeDisponivel;

	public String getAtivoReal() {
		return ativo.substring(0, 5);
	}

	public String print() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-6s",ativo));
		sb.append(";");
		sb.append(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(atualizadoEm));
		sb.append(";");
		sb.append(String.format("%-6s",this.natureza));
		sb.append(";");
		sb.append(String.format("%4d",quantidade.intValue()));
		sb.append(";");
		sb.append(NumberUtils.formatBR(preco));
		return sb.toString();
	}

}
