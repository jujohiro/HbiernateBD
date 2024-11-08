package Vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.MascotaDao;
import entidades.Mascota;
import java.util.List;

public class MascotaGUI extends JFrame implements ActionListener {
    private JTextField txtNombre, txtRaza, txtColor, txtSexo, txtId;
    private JTextArea txtResultado;
    private JList<String> listaMascotas;
    private DefaultListModel<String> listaModel;
    private JButton btnRegistrar, btnConsultar, btnConsultarLista, btnActualizar, btnEliminar;
    MascotaDao miMascotaDao = new MascotaDao();

    public MascotaGUI() {
        setTitle("Gestión de Mascotas");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(true);
        getContentPane().setBackground(new Color(245, 245, 245));

        JPanel panelCampos = new JPanel(new GridLayout(6, 2, 5, 5));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCampos.setBackground(new Color(220, 220, 220));

        JLabel lblId = new JLabel("ID Mascota:");
        lblId.setFont(new Font("Arial", Font.BOLD, 14));
        panelCampos.add(lblId);
        txtId = new JTextField();
        txtId.setPreferredSize(new Dimension(200, 25));
        panelCampos.add(txtId);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        panelCampos.add(lblNombre);
        txtNombre = new JTextField();
        txtNombre.setPreferredSize(new Dimension(200, 25));
        panelCampos.add(txtNombre);

        JLabel lblRaza = new JLabel("Raza:");
        lblRaza.setFont(new Font("Arial", Font.BOLD, 14));
        panelCampos.add(lblRaza);
        txtRaza = new JTextField();
        txtRaza.setPreferredSize(new Dimension(200, 25));
        panelCampos.add(txtRaza);

        JLabel lblColor = new JLabel("Color:");
        lblColor.setFont(new Font("Arial", Font.BOLD, 14));
        panelCampos.add(lblColor);
        txtColor = new JTextField();
        txtColor.setPreferredSize(new Dimension(200, 25));
        panelCampos.add(txtColor);

        JLabel lblSexo = new JLabel("Sexo:");
        lblSexo.setFont(new Font("Arial", Font.BOLD, 14));
        panelCampos.add(lblSexo);
        txtSexo = new JTextField();
        txtSexo.setPreferredSize(new Dimension(200, 25));
        panelCampos.add(txtSexo);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBotones.setBackground(new Color(245, 245, 245));

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnRegistrar.setBackground(new Color(0, 153, 76));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setPreferredSize(new Dimension(110, 30));
        btnRegistrar.addActionListener(this);
        panelBotones.add(btnRegistrar);

        btnConsultar = new JButton("Consultar");
        btnConsultar.setFont(new Font("Arial", Font.BOLD, 12));
        btnConsultar.setBackground(new Color(0, 102, 204));
        btnConsultar.setForeground(Color.WHITE);
        btnConsultar.setPreferredSize(new Dimension(110, 30));
        btnConsultar.addActionListener(this);
        panelBotones.add(btnConsultar);

        btnConsultarLista = new JButton("Consultar Lista");
        btnConsultarLista.setFont(new Font("Arial", Font.BOLD, 12));
        btnConsultarLista.setBackground(new Color(255, 153, 51));
        btnConsultarLista.setForeground(Color.WHITE);
        btnConsultarLista.setPreferredSize(new Dimension(130, 30));
        btnConsultarLista.addActionListener(this);
        panelBotones.add(btnConsultarLista);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 12));
        btnActualizar.setBackground(new Color(204, 204, 0));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setPreferredSize(new Dimension(110, 30));
        btnActualizar.addActionListener(this);
        panelBotones.add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEliminar.setBackground(new Color(204, 0, 0));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setPreferredSize(new Dimension(110, 30));
        btnEliminar.addActionListener(this);
        panelBotones.add(btnEliminar);

        txtResultado = new JTextArea(5, 20);
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Courier New", Font.PLAIN, 12));
        txtResultado.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JScrollPane scrollPane = new JScrollPane(txtResultado);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Resultado"));

        listaModel = new DefaultListModel<>();
        listaMascotas = new JList<>(listaModel);
        listaMascotas.setFont(new Font("Courier New", Font.PLAIN, 12));
        listaMascotas.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JScrollPane scrollPaneLista = new JScrollPane(listaMascotas);
        scrollPaneLista.setBorder(BorderFactory.createTitledBorder("Lista de Mascotas Registradas"));
        scrollPaneLista.setPreferredSize(new Dimension(300, 200));

        add(panelCampos, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(scrollPaneLista, BorderLayout.EAST);
        add(scrollPane, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegistrar) {
            registrarMascota();
        } else if (e.getSource() == btnConsultar) {
            consultarMascota();
        } else if (e.getSource() == btnConsultarLista) {
            consultarListaMascotas();
        } else if (e.getSource() == btnActualizar) {
            actualizarMascota();
        } else if (e.getSource() == btnEliminar) {
            eliminarMascota();
        }
    }

    private void registrarMascota() {
        Mascota miMascota = new Mascota();
        miMascota.setIdMascota(null);
        miMascota.setNombre(txtNombre.getText());
        miMascota.setRaza(txtRaza.getText());
        miMascota.setColorMascota(txtColor.getText());
        miMascota.setSexo(txtSexo.getText());

        txtResultado.setText(miMascotaDao.registrarMascota(miMascota).toString());
        actualizarListaMascotas();
    }

    private void consultarMascota() {
        Long idMascota = Long.parseLong(txtId.getText());
        Mascota miMascota = miMascotaDao.consultarMascota(idMascota);

        if (miMascota != null) {
            txtResultado.setText(miMascota.toString());
        } else {
            txtResultado.setText("No se encontró la mascota");
        }
    }

    private void consultarListaMascotas() {
        List<Mascota> listaMascotas = miMascotaDao.consultarListaMascotas();
        StringBuilder resultado = new StringBuilder("Lista de Mascotas:\n");
        for (Mascota mascota : listaMascotas) {
            resultado.append(mascota.toString()).append("\n");
        }
        txtResultado.setText(resultado.toString());
    }

    private void actualizarMascota() {
        Long idMascota = Long.parseLong(txtId.getText());
        Mascota miMascota = miMascotaDao.consultarMascota(idMascota);

        if (miMascota != null) {
            miMascota.setNombre(txtNombre.getText());
            miMascota.setRaza(txtRaza.getText());
            miMascota.setColorMascota(txtColor.getText());
            miMascota.setSexo(txtSexo.getText());

            txtResultado.setText(miMascotaDao.actualizarMascota(miMascota));
            actualizarListaMascotas();
        } else {
            txtResultado.setText("No se encontró la mascota");
        }
    }

    private void eliminarMascota() {
        Long idMascota = Long.parseLong(txtId.getText());
        Mascota miMascota = miMascotaDao.consultarMascota(idMascota);

        if (miMascota != null) {
            txtResultado.setText(miMascotaDao.eliminarMascota(miMascota));
            actualizarListaMascotas();
        } else {
            txtResultado.setText("No se encontró la mascota");
        }
    }

    private void actualizarListaMascotas() {
        List<Mascota> listaMascotas = miMascotaDao.consultarListaMascotas();
        listaModel.clear();
        for (Mascota mascota : listaMascotas) {
            listaModel.addElement(mascota.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MascotaGUI frame = new MascotaGUI();
            frame.setVisible(true);
        });
    }
}