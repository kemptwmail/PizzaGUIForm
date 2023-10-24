import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame
{
    private JRadioButton thinCrust, regularCrust, deepDish;
    private JComboBox<String> sizeComboBox;
    private JCheckBox[] toppings;
    private JTextArea orderTextArea;
    private JButton orderBtn, clearBtn, quitBtn;

    public PizzaGUIFrame()
    {
        JPanel crustPnl = createCrustPnl();
        JPanel sizePnl = createSizePnl();
        JPanel toppingsPnl = createToppingsPnl();
        JPanel receiptPnl = createReceiptPnl();
        JPanel buttonPnl = createButtonPnl();

        add(crustPnl, BorderLayout.NORTH);
        add(sizePnl, BorderLayout.WEST);
        add(toppingsPnl, BorderLayout.EAST);
        add(receiptPnl, BorderLayout.CENTER);
        add(buttonPnl, BorderLayout.SOUTH);

        pack();
    }

    private JPanel createCrustPnl()
    {
        JPanel pnl = new JPanel();
        pnl.setBorder(BorderFactory.createTitledBorder("Type of Crust"));
        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDish = new JRadioButton("Deep-dish");

        ButtonGroup crustGrp = new ButtonGroup();
        crustGrp.add(thinCrust);
        crustGrp.add(regularCrust);
        crustGrp.add(deepDish);

        pnl.add(thinCrust);
        pnl.add(regularCrust);
        pnl.add(deepDish);
        return pnl;
    }

    private JPanel createSizePnl()
    {
        JPanel pnl = new JPanel();
        pnl.setBorder(BorderFactory.createTitledBorder("Size"));
        String[] size = {"Small", "Medium", "Large", "Super"};
        sizeComboBox = new JComboBox<>(size);
        pnl.add(sizeComboBox);
        return pnl;
    }

    private JPanel createToppingsPnl()
    {
        JPanel pnl = new JPanel();
        pnl.setBorder(BorderFactory.createTitledBorder("Toppings"));
        pnl.setLayout(new GridLayout(0,1));
        String[] topName = {"Ghost Peppers", "Spicy Pepperoni", "Hawaii Pineapple", "Mac & Cheese", "Shredded Italian Cheese", "Mystery Pepper"};
        toppings = new JCheckBox[topName.length];

        for (int i = 0; i < topName.length; i++)
        {
            toppings[i] = new JCheckBox(topName[i]);
            pnl.add(toppings[i]);
        }
        return pnl;
    }

    private JPanel createReceiptPnl()
    {
        JPanel pnl = new JPanel();
        pnl.setBorder(BorderFactory.createTitledBorder("Order Receipt"));
        orderTextArea = new JTextArea(20,40);
        orderTextArea.setEditable(false);
        JScrollPane scrollP = new JScrollPane(orderTextArea);
        pnl.add(scrollP);
        return pnl;
    }

    private JPanel createButtonPnl()
    {
        JPanel pnl = new JPanel();
        orderBtn = new JButton("Order");
        clearBtn = new JButton("Clear");
        quitBtn = new JButton("Quit");

        orderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String crust = "";
                if (thinCrust.isSelected()) {
                    crust = "Thin";
                } else if (regularCrust.isSelected()) {
                    crust = "Regular";
                } else if (deepDish.isSelected()) {
                    crust = "Deep-dish";
                }

                String size = sizeComboBox.getSelectedItem().toString();

                StringBuilder selectedToppings = new StringBuilder();
                double toppingsTotal = 1.0;
                for (int i = 0; i < toppings.length; i++) {
                    if (toppings[i].isSelected()) {
                        selectedToppings.append(toppings[i].getText()).append(", ");
                        toppingsTotal += 1.0;
                    }
                }

                double baseCost = getBaseCost(size);
                double subTotal = baseCost + toppingsTotal;
                double tax = 0.07 * subTotal;
                double total = subTotal + tax;

                String receipt = "===================================================\n" +
                        "Type of Crust & Size: " + crust + " " + size + "\t\tPrice: $" + String.format("%.2f", baseCost) + "\n" +
                        "Ingredients: " + selectedToppings.toString() + "\tPrice: $" + String.format("%.2f", toppingsTotal) + "\n" +
                        "Sub-total: $" + String.format("%.2f", subTotal) + "\n" +
                        "Tax: $" + String.format("%.2f", tax) + "\n" +
                        "--------------------------------------------\n" +
                        "Total: $" + String.format("%.2f", total) + "\n" +
                        "--------------------------------------------\n";
                orderTextArea.setText(receipt);
            }
        });

        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thinCrust.setSelected(false);
                regularCrust.setSelected(false);
                deepDish.setSelected(false);
                sizeComboBox.setSelectedIndex(0);

                for (JCheckBox topping : toppings) {
                    topping.setSelected(false);
                }
                orderTextArea.setText("");

            }
        });

        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Confirm Quit", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION)
                {
                    System.exit(0);
                }

            }
        });

        pnl.add(orderBtn);
        pnl.add(clearBtn);
        pnl.add(quitBtn);
        return pnl;
    }

    private double getBaseCost(String size)
    {
        double smallBase = 8.00;
        double mediumBase = 12.00;
        double largeBase = 16.00;
        double superBase = 20.00;

        String selectedSize = sizeComboBox.getSelectedItem().toString();
        double priceSz = 0.0;


        if ("Small".equals(selectedSize)) {
            priceSz = smallBase;
        } else if ("Medium".equals(selectedSize)) {
            priceSz = mediumBase;
        } else if ("Large".equals(selectedSize)) {
            priceSz = largeBase;
        } else if ("Super".equals(selectedSize)) {
            priceSz = superBase;
        }
        return priceSz;


    }



}
