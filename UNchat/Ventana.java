package UNchat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Ventana extends JFrame {

    protected JTextArea jTextArea1;
    protected JScrollPane jScrollPane1;
    protected JTextField texto;
    protected JButton boton;

    public Ventana(String title) {
        setTitle(title);  // Título de la ventana (Cliente o Servidor)
        setSize(400, 300);
        setLocationRelativeTo(null); // Centra la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear los componentes de la interfaz
        jTextArea1 = new JTextArea();
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setEditable(false);  // No será editable
        jScrollPane1 = new JScrollPane(jTextArea1);

        texto = new JTextField(20);
        boton = new JButton("Enviar");

        // Configurar el layout
        setLayout(new BorderLayout());
        add(jScrollPane1, BorderLayout.CENTER);
        JPanel panelSur = new JPanel();
        panelSur.setLayout(new FlowLayout());
        panelSur.add(texto);
        panelSur.add(boton);
        add(panelSur, BorderLayout.SOUTH);

        pack();
    }

    // Este método debe ser implementado por las clases hijas para enviar mensajes.
    public void addActionListener(ActionListener listener) {
        boton.addActionListener(listener);
    }

    public void appendMessage(String message) {
        jTextArea1.append(message + "\n");
    }
}
