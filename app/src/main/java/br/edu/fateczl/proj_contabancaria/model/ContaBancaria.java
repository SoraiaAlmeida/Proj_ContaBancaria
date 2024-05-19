package br.edu.fateczl.proj_contabancaria.model;

public class ContaBancaria {

    public String cliente;
    public int num_conta;
    public  float saldo;

    public ContaBancaria(String cliente, int num_conta, float saldo) {
        this.cliente = cliente;
        this.num_conta = num_conta;
        this.saldo = saldo;
}
    public boolean sacar(float valor) {
        if (saldo >= valor) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    public void depositar(float valor) {
        saldo += valor;
    }
    public float getSaldo() {
        return saldo;
    }

    @Override
    public String toString() {
        return "Cliente: " + cliente + ", Conta: " + num_conta + ", Saldo: " + saldo;
    }
}
