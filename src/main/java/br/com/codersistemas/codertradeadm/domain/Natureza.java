package br.com.codersistemas.codertradeadm.domain;

public enum Natureza {
	
	COMPRA("Compra"),VENDA("Venda");
	
	String descricao;
	
	Natureza(String descricao){
		this.descricao = descricao;
	}
	
	public static Natureza fromDescricao(String descricao) {
		Natureza[] values = values();
		for (Natureza tipo : values) {
			if(tipo.getDescricao().equals(descricao)) {
				return tipo;
			}
		}
		return null;
	}
	
	String getDescricao() {
		return descricao;
	}
}
