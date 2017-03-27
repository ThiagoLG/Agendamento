package dao;

import model.Cliente;

public interface IClienteDao {
	
	public boolean cadastrado(String cpf);
	
	public boolean cadastrarCliente(Cliente c);
	
	public Cliente getCliente(String cpf);

}
