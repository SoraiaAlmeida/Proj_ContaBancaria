package br.edu.fateczl.proj_contabancaria;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import br.edu.fateczl.proj_contabancaria.model.ContaBancaria;
import br.edu.fateczl.proj_contabancaria.model.ContaEspecial;
import br.edu.fateczl.proj_contabancaria.model.ContaPoupanca;

public class MainActivity extends AppCompatActivity {

    private EditText etCliente, etNumConta, etSaldo, etDiaRendimento, etLimite, etValorOperacao;
    private RadioGroup rgTipoConta;
    private RadioButton rbPoupanca, rbEspecial;
    private Button btnCriarConta, btnSacar, btnDepositar, btnCalcularRendimento;
    private TextView tvResultado;
    private ContaBancaria conta;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.etLimite), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etCliente = findViewById(R.id.etCliente);
        etNumConta = findViewById(R.id.etNumConta);
        etSaldo = findViewById(R.id.etSaldo);
        etDiaRendimento = findViewById(R.id.etDiaRendimento);
        etLimite = findViewById(R.id.etLimite);
        etValorOperacao = findViewById(R.id.etValorOperacao);

        rgTipoConta = findViewById(R.id.rgTipoConta);
        rbPoupanca = findViewById(R.id.rbPoupanca);
        rbEspecial = findViewById(R.id.rbEspecial);

        btnCriarConta = findViewById(R.id.btnCriarConta);
        btnSacar = findViewById(R.id.btnSacar);
        btnDepositar = findViewById(R.id.btnDepositar);
        btnCalcularRendimento = findViewById(R.id.btnCalcularRendimento);

        tvResultado = findViewById(R.id.tvResultado);

        btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarConta();
                limparCampos();
            }
        });

        btnSacar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sacar();
                limparCampos();
            }
        });

        btnDepositar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                depositar();
                limparCampos();
            }
        });

        btnCalcularRendimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularRendimento();
                limparCampos();
            }
        });

        rgTipoConta.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbPoupanca) {
                    etDiaRendimento.setVisibility(View.VISIBLE);
                    etLimite.setVisibility(View.GONE);
                    btnCalcularRendimento.setVisibility(View.VISIBLE);
                    etValorOperacao.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.rbEspecial) {
                    etDiaRendimento.setVisibility(View.GONE);
                    etLimite.setVisibility(View.VISIBLE);
                    btnCalcularRendimento.setVisibility(View.GONE);
                    etValorOperacao.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void criarConta() {
        String cliente = etCliente.getText().toString();
        int numConta = Integer.parseInt(etNumConta.getText().toString());
        float saldo = Float.parseFloat(etSaldo.getText().toString());

        if (rbPoupanca.isChecked()) {
            int diaRendimento = Integer.parseInt(etDiaRendimento.getText().toString());
            conta = new ContaPoupanca(cliente, numConta, saldo, diaRendimento);
        } else if (rbEspecial.isChecked()) {
            float limite = Float.parseFloat(etLimite.getText().toString());
            conta = new ContaEspecial(cliente, numConta, saldo, limite);
        }

        tvResultado.setText("Conta criada: " + conta.toString());
    }

    private void sacar() {
        if (conta != null) {
            try {
                float valor = Float.parseFloat(etValorOperacao.getText().toString());
                boolean sucesso = conta.sacar(valor);
                if (sucesso) {
                    tvResultado.setText("Saque realizado com sucesso! Novo saldo: " + conta.getSaldo());
                } else {
                    tvResultado.setText("Saque não realizado. Saldo insuficiente ou acima do limite.");
                }
            } catch (NumberFormatException e) {
                tvResultado.setText("Erro: valor inválido.");
            }
        } else {
            tvResultado.setText("Conta não localizada.");
        }
    }

    private void depositar() {
        if (conta != null) {
            try {
                float valor = Float.parseFloat(etValorOperacao.getText().toString());
                conta.depositar(valor);
                tvResultado.setText("Depósito realizado com sucesso! Novo saldo: " + conta.getSaldo());
            } catch (NumberFormatException e) {
                tvResultado.setText("Erro: valor inválido.");
            }
        } else {
            tvResultado.setText("Conta não localizada.");
        }
    }

    private void calcularRendimento() {
        if (conta instanceof ContaPoupanca) {
            ContaPoupanca contaPoupanca = (ContaPoupanca) conta;
            float taxa = Float.parseFloat(etValorOperacao.getText().toString());
            contaPoupanca.calcularNovoSaldo(taxa);
            tvResultado.setText("Rendimento calculado. Novo saldo: " + contaPoupanca.getSaldo());
        } else {
            tvResultado.setText("Não é uma conta poupança.");
        }
    }

    private void limparCampos() {
        etCliente.getText().clear();
        etNumConta.getText().clear();
        etSaldo.getText().clear();
        etDiaRendimento.getText().clear();
        etLimite.getText().clear();
        etValorOperacao.getText().clear();
    }
}