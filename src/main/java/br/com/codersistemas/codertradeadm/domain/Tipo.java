package br.com.codersistemas.codertradeadm.domain;

public enum Tipo {
	
	LIMITADA("Limitada"), STOP("Stop");
	
	String descricao;
	
	Tipo(String descricao){
		this.descricao = descricao;
	}
	
	public static Tipo fromDescricao(String descricao) {
		Tipo[] values = values();
		for (Tipo tipo : values) {
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
