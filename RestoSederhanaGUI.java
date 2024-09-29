package Belajar_Java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RestoSederhanaGUI extends JFrame {

    private JComboBox<String> menuComboBox;
    private JTextField jumlahField;
    private JTextField bayarField;
    private JLabel totalLabel;
    private JLabel kembalianLabel;
    private JTextArea strukArea;
    private JButton resetButton;
    private boolean isDarkMode = false;  // Untuk melacak mode tema (gelap/terang)

    public RestoSederhanaGUI() {
        setTitle("Restoran Sederhana");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Tombol untuk toggle antara mode terang dan gelap
        JButton toggleThemeButton = new JButton("Dark Mode");
        toggleThemeButton.setBounds(300, 10, 120, 25);
        add(toggleThemeButton);

        JLabel menuLabel = new JLabel("Pilih Menu:");
        menuLabel.setBounds(20, 50, 100, 25);
        add(menuLabel);

        String[] menuOptions = {"1. Gado-gado - Rp.15000", "2. Soto Ayam - Rp.15500", "3. Sate Ayam - Rp.20000", 
                                "4. Sate Kambing - Rp.18000", "5. Sate Lembu - Rp.23000", "6. Sate Kuda - Rp.20000"};
        menuComboBox = new JComboBox<>(menuOptions);
        menuComboBox.setBounds(150, 50, 250, 25);
        add(menuComboBox);

        JLabel jumlahLabel = new JLabel("Jumlah Pesanan:");
        jumlahLabel.setBounds(20, 90, 120, 25);
        add(jumlahLabel);

        jumlahField = new JTextField();
        jumlahField.setBounds(150, 90, 250, 25);
        add(jumlahField);

        JLabel bayarLabel = new JLabel("Bayar:");
        bayarLabel.setBounds(20, 130, 120, 25);
        add(bayarLabel);

        bayarField = new JTextField();
        bayarField.setBounds(150, 130, 250, 25);
        add(bayarField);

        JButton hitungButton = new JButton("Hitung Total");
        hitungButton.setBounds(20, 170, 150, 25);
        add(hitungButton);

        resetButton = new JButton("Reset Pesanan");
        resetButton.setBounds(200, 170, 150, 25);
        add(resetButton);

        totalLabel = new JLabel("Total: Rp.0");
        totalLabel.setBounds(20, 210, 200, 25);
        add(totalLabel);

        kembalianLabel = new JLabel("");
        kembalianLabel.setBounds(20, 240, 300, 25);
        add(kembalianLabel);

        strukArea = new JTextArea();
        strukArea.setBounds(20, 280, 380, 150);
        strukArea.setEditable(false);
        add(strukArea);

        // Event listener untuk tombol hitung total
        hitungButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitungTotal();
            }
        });

        // Event listener untuk tombol reset
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });

        // Event listener untuk tombol toggle mode
        toggleThemeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleTheme(toggleThemeButton);
            }
        });
    }

    private void hitungTotal() {
        String menu = (String) menuComboBox.getSelectedItem();
        int harga = 0;

        if (menu.contains("Gado-gado")) {
            harga = 15000;
        } else if (menu.contains("Soto Ayam")) {
            harga = 15500;
        } else if (menu.contains("Sate Ayam")) {
            harga = 20000;
        } else if (menu.contains("Sate Kambing")) {
            harga = 18000;
        } else if (menu.contains("Sate Lembu")) {
            harga = 23000;
        } else if (menu.contains("Sate Kuda")) {
            harga = 20000;
        }

        try {
            int jumlah = Integer.parseInt(jumlahField.getText());
            int bayar = Integer.parseInt(bayarField.getText());

            // Validasi Pemesanan Minimum
            if (jumlah <= 0) {
                JOptionPane.showMessageDialog(this, "Jumlah pesanan minimal 1", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int total = harga * jumlah;
            double diskon = 0.0;

            // Diskon 10% untuk total di atas Rp.50.000
            if (total > 50000) {
                diskon = total * 0.10;
            }

            double totalSetelahDiskon = total - diskon;
            totalLabel.setText("Total: Rp." + totalSetelahDiskon);

            if (bayar >= totalSetelahDiskon) {
                int kembalian = (int) (bayar - totalSetelahDiskon);
                kembalianLabel.setText("Kembalian Anda: Rp." + kembalian);
                tampilkanStruk(menu, jumlah, total, diskon, totalSetelahDiskon, bayar, kembalian);
                simpanTransaksi(menu, jumlah, total, diskon, totalSetelahDiskon, bayar, kembalian);
            } else {
                kembalianLabel.setText("Uang Anda Kurang: Rp." + (totalSetelahDiskon - bayar));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harap masukkan angka yang valid", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Fitur Simpan Riwayat Transaksi
    private void simpanTransaksi(String menu, int jumlah, double total, double diskon, double totalSetelahDiskon, int bayar, int kembalian) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("riwayat_transaksi.txt", true))) {
            writer.write("Menu: " + menu);
            writer.newLine();
            writer.write("Jumlah: " + jumlah);
            writer.newLine();
            writer.write("Total Sebelum Diskon: Rp." + total);
            writer.newLine();
            writer.write("Diskon: Rp." + diskon);
            writer.newLine();
            writer.write("Total Setelah Diskon: Rp." + totalSetelahDiskon);
            writer.newLine();
            writer.write("Bayar: Rp." + bayar);
            writer.newLine();
            writer.write("Kembalian: Rp." + kembalian);
            writer.newLine();
            writer.write("-------------------------------");
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan riwayat transaksi", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Fitur Tampilkan Struk Otomatis
    private void tampilkanStruk(String menu, int jumlah, double total, double diskon, double totalSetelahDiskon, int bayar, int kembalian) {
        strukArea.setText("");
        strukArea.append("====== STRUK PEMBAYARAN ======\n");
        strukArea.append("Menu: " + menu + "\n");
        strukArea.append("Jumlah: " + jumlah + "\n");
        strukArea.append("Total Sebelum Diskon: Rp." + total + "\n");
        strukArea.append("Diskon: Rp." + diskon + "\n");
        strukArea.append("Total Setelah Diskon: Rp." + totalSetelahDiskon + "\n");
        strukArea.append("Bayar: Rp." + bayar + "\n");
        strukArea.append("Kembalian: Rp." + kembalian + "\n");
        strukArea.append("============================\n");
    }

    // Fitur Reset Pesanan
    private void resetForm() {
        menuComboBox.setSelectedIndex(0);
        jumlahField.setText("");
        bayarField.setText("");
        totalLabel.setText("Total: Rp.0");
        kembalianLabel.setText("");
        strukArea.setText("");
    }

    // Fitur Toggle Mode (Gelap/Terang)
    private void toggleTheme(JButton toggleButton) {
        if (!isDarkMode) {
            // Ubah ke Mode Gelap
            getContentPane().setBackground(Color.DARK_GRAY);
            menuComboBox.setBackground(Color.LIGHT_GRAY);
            menuComboBox.setForeground(Color.WHITE);
            jumlahField.setBackground(Color.LIGHT_GRAY);
            jumlahField.setForeground(Color.WHITE);
            bayarField.setBackground(Color.LIGHT_GRAY);
            bayarField.setForeground(Color.WHITE);
            totalLabel.setForeground(Color.WHITE);
            kembalianLabel.setForeground(Color.WHITE);
            strukArea.setBackground(Color.LIGHT_GRAY);
            strukArea.setForeground(Color.WHITE);
            resetButton.setBackground(Color.GRAY);
            resetButton.setForeground(Color.WHITE);
            toggleButton.setText("Light Mode");
            toggleButton.setBackground(Color.GRAY);
            toggleButton.setForeground(Color.WHITE);
            isDarkMode = true;
        } else {
            // Ubah ke Mode Terang
            getContentPane().setBackground(Color.WHITE);
            menuComboBox.setBackground(Color.WHITE);
            menuComboBox.setForeground(Color.BLACK);
            jumlahField.setBackground(Color.WHITE);
            jumlahField.setForeground(Color.BLACK);
            bayarField.setBackground(Color.WHITE);
            bayarField.setForeground(Color.BLACK);
            totalLabel.setForeground(Color.BLACK);
            kembalianLabel.setForeground(Color.BLACK);
            strukArea.setBackground(Color.WHITE);
            strukArea.setForeground(Color.BLACK);
            resetButton.setBackground(Color.LIGHT_GRAY);
            resetButton.setForeground(Color.BLACK);
            toggleButton.setText("Dark Mode");
            toggleButton.setBackground(Color.LIGHT_GRAY);
            toggleButton.setForeground(Color.BLACK);
            isDarkMode = false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RestoSederhanaGUI().setVisible(true);
            }
        });
    }
}
