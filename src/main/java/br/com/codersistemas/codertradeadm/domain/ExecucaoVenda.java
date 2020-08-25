package br.com.codersistemas.codertradeadm.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "execucao_venda")
public class ExecucaoVenda implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "seq_execucao_venda", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "seq_execucao_venda", initialValue = 1000, allocationSize = 1)
	@Column(name = "id_execucao_venda", nullable = false)
	private Long id;

	@ManyToOne(cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	@JoinColumn(name="id_ordem", nullable=false)
	private Ordem compra;
	
	@NotNull(message="Valor Unitário da Compra deve ser preenchido")
	@Column(name="valor_unitario_compra", precision=10, scale=2, nullable=false)
	private BigDecimal valorUnitarioCompra;
	
	@NotNull(message="Valor Unitário da Venda deve ser preenchido")
	@Column(name="valor_unitario_venda", precision=10, scale=2, nullable=false)
	private BigDecimal valorUnitarioVenda;
	
	@NotNull(message="Quantidade deve ser preenchido")
	@Column(name="quantidade", precision=10, scale=0, nullable=false)
	private BigDecimal quantidade;
	
	@NotNull(message="Valor Total da Compra deve ser preenchido")
	@Column(name="valor_total_compra", precision=10, scale=0, nullable=false)
	private BigDecimal valorTotalCompra;
	
	@NotNull(message="Valor Total da Venda deve ser preenchido")
	@Column(name="valor_total_venda", precision=10, scale=0, nullable=false)
	private BigDecimal valorTotalVenda;
	
	@NotNull(message="Resultado deve ser preenchido")
	@Column(name="resultado", precision=10, scale=0, nullable=false)
	private BigDecimal resultado;
	
}
