package com.edsc.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edsc.cursomc.domain.ItemPedido;
import com.edsc.cursomc.domain.PagamentoComBoleto;
import com.edsc.cursomc.domain.Pedido;
import com.edsc.cursomc.domain.Produto;
import com.edsc.cursomc.domain.enums.EstadoPagamento;
import com.edsc.cursomc.repositories.ItemPedidoRepository;
import com.edsc.cursomc.repositories.PagamentoRepository;
import com.edsc.cursomc.repositories.PedidoRepository;
import com.edsc.cursomc.repositories.ProdutoRepository;
import com.edsc.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo:" + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip: obj.getItens()) {
			 ip.setDesconto(0.0);
			 Produto p = produtoRepository.findById(ip.getProduto().getId()).get(); 
			 ip.setPreco(p.getPreco());
			 ip.setPedido(obj);
		}
		this.itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
}
