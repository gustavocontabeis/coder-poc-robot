package br.com.codersistemas.codertradeadm.domain;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ExecucaoVenda {
	Ordem compra;
	Ordem venda;
	BigDecimal quantidadeExecutada;
}
