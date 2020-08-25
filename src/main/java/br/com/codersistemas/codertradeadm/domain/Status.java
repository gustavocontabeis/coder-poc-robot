package br.com.codersistemas.codertradeadm.domain;

public enum Status {
	
	EXECUTADA("Executada"), REJEITADA("Rejeitada"), CANCELADA("Cancelada");
	
	String descricao;
	
	Status(String descricao){
		this.descricao = descricao;
	}
	
	public static Status fromDescricao(String descricao) {
		Status[] values = values();
		for (Status tipo : values) {
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
