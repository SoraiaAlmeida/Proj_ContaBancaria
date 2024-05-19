package br.edu.fateczl.proj_contabancaria.model;

public class ContaPoupanca extends ContaBancaria {
    private int diaDeRendimento;

    public ContaPoupanca(String cliente, int num_conta, float saldo, int diaDeRendimento) {
        super(cliente, num_conta, saldo);
        this.diaDeRendimento = diaDeRendimento;
    }

    public void calcularNovoSaldo(float taxaRendimento) {
        this.saldo += this.saldo * (taxaRendimento / 100);
    }
}
