package br.edu.fateczl.proj_contabancaria.model;

public class ContaEspecial extends ContaBancaria {
    private float limite;

    public ContaEspecial(String cliente, int numConta, float saldo, float limite) {
        super(cliente, numConta, saldo);
        this.limite = limite;
    }

    @Override
    public boolean sacar(float valor) {
        if (saldo + limite >= valor) {
            saldo -= valor;
            return true;
        }
        return false;
    }
}
