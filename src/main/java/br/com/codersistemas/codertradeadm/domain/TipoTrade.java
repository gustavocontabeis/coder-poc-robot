package br.com.codersistemas.codertradeadm.domain;

public enum TipoTrade {
	
	SWING("Swing Trade"), DAY("Day Trade");
	
	String descricao;
	
	TipoTrade(String descricao){
		this.descricao = descricao;
	}
	
	public static TipoTrade fromDescricao(String descricao) {
		TipoTrade[] values = values();
		for (TipoTrade tipo : values) {
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
