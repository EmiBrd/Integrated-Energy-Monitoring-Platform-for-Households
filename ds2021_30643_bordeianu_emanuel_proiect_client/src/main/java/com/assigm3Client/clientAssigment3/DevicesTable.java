package com.assigm3Client.clientAssigment3;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class DevicesTable {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JLabel lbl_device_selectat;
    private JLabel lbl_timp_inceput_program;
    private JLabel lbl_timp_sfarsit_program;
    private JLabel lbl_introducereNrOreProgram;
    private JTextField textField_introd_nr_ore_program;
    private JLabel lbl_selectati_un_device;
    private Long idClient;
    private MeasureValue measureValue;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //new DevicesTable();
                    //DevicesTable window = new DevicesTable();
                    //window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public DevicesTable(Long idClient) {
        initialize(idClient);
        frame.setVisible(true);
    }


    private void initialize(Long idClient) {
        ConfigurableApplicationContext ctx = SpringApplication.run(ClientAssigment3Application.class);
        measureValue = ctx.getBean(MeasureValue.class);


        frame = new JFrame();
        frame.setBounds(100, 100, 1057, 529);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(100, 149, 237));
        panel.setBounds(0, 0, 1043, 492);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(257, 20, 776, 462);
        panel.add(scrollPane);

        table = new JTable();
        table.setBackground(new Color(169, 169, 169));
        model = new DefaultTableModel();
        Object[] coloana = {"ID_Device", "Description", "AddressLocation", "MaxEnergConsum", "AvgBaselineEnergConsum"};
        Object[] rand = new Object[5];
        model.setColumnIdentifiers(coloana);
        table.setModel(model);
        scrollPane.setViewportView(table);

        JButton btnShowDevices = new JButton("Afisare_Device-uri");
        btnShowDevices.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<String[]> devicesList = measureValue.sendDevicesToClient(idClient);
                int i = 0;
                for (String[] d : devicesList) {
                    System.out.println("d[0]=" + d[0] + ", d[1]=" + d[1] + ", d[2]=" + d[2] + ", d[3]=" + d[3] + ", d[4]=" + d[4]);
//					Long idBun = Long.parseLong(d[0]);
//					String description = d[1];
//					String addressLocation = d[2];
//					Double maxEnerCons = Double.parseDouble(d[3]);
//					Double avgBaselineEnerCons = Double.parseDouble(d[4]);
                    rand[0] = Long.parseLong(d[0]);        // ID
                    rand[1] = d[1];        // Description
                    rand[2] = d[2];        // AddressLocation
                    rand[3] = Double.parseDouble(d[3]);        // maxEnerCons
                    rand[4] = Double.parseDouble(d[4]);        // avgBaselineEnerCons
                    model.addRow(rand);
                    i++;
                }
            }
        });
        btnShowDevices.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnShowDevices.setBounds(10, 20, 237, 45);
        panel.add(btnShowDevices);

        JButton btnAlegereDevice_1 = new JButton("Alegere_Device");
        btnAlegereDevice_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int i = table.getSelectedRow();
                if (i >= 0) {
                    lbl_device_selectat.setText("Device-ul selectat este pe randul: " + i);
                    System.out.println("Subpunct3 si 4 din meniu");
                    DefaultCategoryDataset datasetSubpunct3Si4 = new DefaultCategoryDataset();
                    Double maxEnerCons = (Double) model.getValueAt(i, 3);
                    System.out.println("maxEnerCons = " + maxEnerCons);
                    Integer[] timpi = measureValue.getBestTimeToStartSubpunct4(idClient, Integer.parseInt(textField_introd_nr_ore_program.getText()));
                    lbl_timp_inceput_program.setText("Timp inceput = " + timpi[0].toString());
                    lbl_timp_sfarsit_program.setText("Timp sfarsit = " + timpi[1].toString());
                    Double[] avgBaselineAllDevices = measureValue.getAverageEnergConsSubpunct2(idClient);
                    XYSeriesCollection datasettt = new XYSeriesCollection();
                    XYSeries[] xySeries = new XYSeries[1];
                    xySeries[0] = new XYSeries("Object " + 1);
                    XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

                    for (int j = 0; j < 24; j++) {
                        if ((j >= timpi[0]) && (j <= timpi[1])) {
                            avgBaselineAllDevices[j] += maxEnerCons;
                        }
                        datasetSubpunct3Si4.setValue(avgBaselineAllDevices[i], "", String.valueOf(i));
                        double aux = avgBaselineAllDevices[j];
                        xySeries[0].add(j, aux);
                        System.out.println("avgBaselineAllDevices subpunct3 si 4[" + i + "] = " + avgBaselineAllDevices[j]);
                    }

//						XYSeriesCollection datasettt = new XYSeriesCollection();
//						XYSeries[] xySeries = new XYSeries[1];
//						xySeries[0] = new XYSeries("Object " + 1);
//						XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

//						for(int j = timpi[0]; j<=timpi[1]; j++) {
//							double aux = avgBaselineAllDevices[j];
//							xySeries[0].add(j, aux);
//							System.out.println("averageMeasurementsOverWeek["+j+"] = " + avgBaselineAllDevices[j]);
//						}
                    datasettt.addSeries(xySeries[0]);

                    new LineChartt("Subpunctele 3 si 4", "XAxis", "YAxis",
                            datasettt, xySeries, 1, renderer.getPlot(), renderer).setVisible(true);

                    JOptionPane.showMessageDialog(null, "Device selected successfully");

                } else {
                    JOptionPane.showMessageDialog(null, "Please select a device");
                }

            }
        });
        btnAlegereDevice_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnAlegereDevice_1.setBounds(10, 270, 237, 45);
        panel.add(btnAlegereDevice_1);

        lbl_device_selectat = new JLabel("Device_Selectat");
        lbl_device_selectat.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_device_selectat.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lbl_device_selectat.setBounds(10, 316, 237, 31);
        panel.add(lbl_device_selectat);

        lbl_timp_inceput_program = new JLabel("Timp_inceput_program");
        lbl_timp_inceput_program.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_timp_inceput_program.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lbl_timp_inceput_program.setBounds(10, 370, 237, 31);
        panel.add(lbl_timp_inceput_program);

        lbl_timp_sfarsit_program = new JLabel("Timp_sfarsit_program");
        lbl_timp_sfarsit_program.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_timp_sfarsit_program.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lbl_timp_sfarsit_program.setBounds(10, 411, 237, 31);
        panel.add(lbl_timp_sfarsit_program);

        lbl_introducereNrOreProgram = new JLabel("Introducere_Nr_Ore_Program");
        lbl_introducereNrOreProgram.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_introducereNrOreProgram.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lbl_introducereNrOreProgram.setBounds(10, 75, 237, 31);
        panel.add(lbl_introducereNrOreProgram);

        textField_introd_nr_ore_program = new JTextField();
        textField_introd_nr_ore_program.setHorizontalAlignment(SwingConstants.CENTER);
        textField_introd_nr_ore_program.setFont(new Font("Tahoma", Font.PLAIN, 15));
        textField_introd_nr_ore_program.setBounds(10, 116, 237, 37);
        panel.add(textField_introd_nr_ore_program);
        textField_introd_nr_ore_program.setColumns(10);

        JButton btn_submit_nr_ore_program = new JButton("Submit_Nr_Ore_Program");
        btn_submit_nr_ore_program.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textField_introd_nr_ore_program.setText(textField_introd_nr_ore_program.getText());
                int valoareTextField = Integer.parseInt(textField_introd_nr_ore_program.getText());
                if (textField_introd_nr_ore_program.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Trebuie sa introduceti o valoare intre 0 si 23");
                }
                if ((valoareTextField > 23) || (valoareTextField < 0)) {
                    JOptionPane.showMessageDialog(null, "Valoarea introdusa nu poate fi mai mare decat 23 si mai mica decat 0");
                } else {
                    JOptionPane.showMessageDialog(null, "Numar ore selectat cu succes");
                }

            }
        });
        btn_submit_nr_ore_program.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btn_submit_nr_ore_program.setBounds(10, 163, 237, 45);
        panel.add(btn_submit_nr_ore_program);

        lbl_selectati_un_device = new JLabel("Selectati un device");
        lbl_selectati_un_device.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_selectati_un_device.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lbl_selectati_un_device.setBounds(10, 233, 237, 31);
        panel.add(lbl_selectati_un_device);


        ctx.close();
    }

}

