package com.conversor.igu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FraameApp extends JFrame {

	private JPanel contentPane;
	private JTextField header;
	private JTextField footer;
	private JTextField input;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JComboBox fromUnit;
	private JComboBox toUnit;
	private JTextField output;
	private JComboBox tipoConversion;

	private String[] TipoConversion = { "Moneda", "Temperatura", "Longitud" };
	private String[] moneda = { "USD", "ARS", "VEF" };
	private String[] temperatura = { "CELSIUS", "FAHRENHEIT" };
	private String[] distancia = { "METROS", "CENTIMETROS", "MILIMETROS" };
	
	double usdArs= 540;
	double usdVef= 31.3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FraameApp frame = new FraameApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FraameApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(3, 0, 0, 0));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		header = new JTextField();
		header.setBounds(10, 11, 356, 26);
		header.setFont(new Font("Tahoma", Font.PLAIN, 16));
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setText("Conversor  Desafio Alura/Oracle");
		contentPane.add(header);
		header.setColumns(25);

		footer = new JTextField();
		footer.setText("Creado By Leonardo Vargas");
		footer.setHorizontalAlignment(SwingConstants.CENTER);
		footer.setBounds(10, 324, 356, 26);
		contentPane.add(footer);
		footer.setColumns(10);

		JLabel lblNewLabel = new JLabel("Seleccione : ");
		lblNewLabel.setBounds(85, 48, 76, 26);
		contentPane.add(lblNewLabel);

		tipoConversion = new JComboBox(TipoConversion);

		tipoConversion.setBounds(171, 50, 99, 22);
		contentPane.add(tipoConversion);

		input = new JTextField();
		input.setHorizontalAlignment(SwingConstants.CENTER);
		input.setBounds(35, 118, 111, 20);
		contentPane.add(input);
		input.setColumns(10);

		lblNewLabel_1 = new JLabel("Cantidad:");
		lblNewLabel_1.setBounds(35, 96, 76, 20);
		contentPane.add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("De:");
		lblNewLabel_2.setBounds(35, 161, 46, 14);
		contentPane.add(lblNewLabel_2);

		lblNewLabel_3 = new JLabel("Para:");
		lblNewLabel_3.setBounds(35, 233, 46, 14);
		contentPane.add(lblNewLabel_3);

		fromUnit = new JComboBox(moneda);
		fromUnit.setBounds(35, 183, 111, 22);
		contentPane.add(fromUnit);

		toUnit = new JComboBox(moneda);
		toUnit.setBounds(35, 258, 111, 22);
		contentPane.add(toUnit);

		output = new JTextField();
		output.setHorizontalAlignment(SwingConstants.CENTER);
		output.setEditable(false);
		output.setBounds(228, 191, 89, 20);
		contentPane.add(output);
		output.setColumns(10);

		JButton convertir = new JButton("Convertir");
		convertir.setBounds(228, 157, 89, 23);
		contentPane.add(convertir);

		convertir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == convertir) {
					convertir();
				}
			}
		});

		tipoConversion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == tipoConversion) {
					updateUnits();
				}
			}
		});

	}

	private void updateUnits() {
		String[] units;

		if (tipoConversion.getSelectedItem().equals("Temperatura")) {
			units = temperatura;

		} else if (tipoConversion.getSelectedItem().equals("Moneda")) {
			units = moneda;

		} else {
			units = distancia;
		}

		fromUnit.setModel(new DefaultComboBoxModel<>(units));
		toUnit.setModel(new DefaultComboBoxModel<>(units));
	}

	private void convertir() {
		try {
			double inputValue = Double.parseDouble(input.getText());
			String from = fromUnit.getSelectedItem().toString();
			String to = toUnit.getSelectedItem().toString();
			double result;

			if (tipoConversion.getSelectedItem().equals("Temperatura")) {

				result = convertirTemperatura(inputValue, from, to);

			}

			else if (tipoConversion.getSelectedItem().equals("Moneda")) {
				result = convertirMoneda(inputValue, from, to);
			}

			else {
				result = convertirDistancia(inputValue, from, to);
			}

			output.setText(String.valueOf(result));
		} catch (NumberFormatException ex) {
			output.setText("valor inválido");
		}

	}

	private double convertirMoneda(double inputValue, String from, String to) {

		double resultado;
		

		String key = from + "-" + to;

		switch (key) {
		case "USD-ARS":
			resultado = inputValue * usdArs;
			return redondear(resultado);

		case "ARS-USD":
			resultado = inputValue / usdArs;
			return redondear(resultado);
			
		case "USD-VEF":
			resultado = inputValue * usdVef;
			return redondear(resultado);

		case "VEF-USD":
			resultado = inputValue / usdVef;
			return redondear(resultado);
			
		case "VEF-ARS":
			resultado = (inputValue /usdVef)*usdArs;
			return redondear(resultado);

		case "ARS-VEF":
			resultado = (inputValue / usdArs)*usdVef;
			return redondear(resultado);

		default:
			return resultado = inputValue;

		}

	}

	private double convertirTemperatura(double inputValue, String from, String to) {

		double resultado;

		if (from.equals("CELSIUS") && to.equals("FAHRENHEIT")) {

			resultado = (inputValue * 9 / 5) + 32;
			return redondear(resultado);

		} else if (from.equals("FAHRENHEIT") && to.equals("CELSIUS")) {
			resultado = (inputValue - 32) * 5 / 9;
			return redondear(resultado);
		}
		return inputValue;
	}

	// todo completar el metodo convertir distancia
	private double convertirDistancia(double inputValue, String from, String to) {

		double resultado;
		String key = from + "-" + to;

		switch (key) {
		case "METROS-CENTIMETROS":
			resultado = inputValue * 100;
			return redondear(resultado);

		case "CENTIMETROS-METROS":
			resultado = inputValue / 100;
			return redondear(resultado);
			
		case "METROS-MILIMETROS":
			resultado = inputValue * 1000;
			return redondear(resultado);

		case "MILIMETROS-METROS":
			resultado = inputValue / 1000;
			return redondear(resultado);
			
		case "CENTIMETROS-MILIMETROS":
			resultado = inputValue * 10;
			return redondear(resultado);

		case "MILIMETROS-CENTIMETROS":
			resultado = inputValue / 10;
			return redondear(resultado);

		default:
			return resultado = inputValue;

		}

	}

	public double redondear(double valor) {
		return Math.round(valor * 100.0) / 100.0;
	}

}
