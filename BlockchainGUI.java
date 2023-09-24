import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BlockchainGUI {

    private JFrame frame;
    private JTextField senderField;
    private JTextField recipientField;
    private JTextField amountField;
    private JTextArea blockchainDisplay;
    private JTextArea transactionsDisplay;
    private JButton addTransactionButton;
    private JButton addBlockButton;
    private JButton validateChainButton;

    private List<Transaction> currentTransactions = new ArrayList<>();
    private Blockchain blockchain = new Blockchain(4); // Assuming difficulty of 4 for simplicity

    public BlockchainGUI() {
        frame = new JFrame("Blockchain GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new FlowLayout());

        senderField = new JTextField(20);
        recipientField = new JTextField(20);
        amountField = new JTextField(20);

        addTransactionButton = new JButton("Add Transaction");
        addBlockButton = new JButton("Add Block");
        validateChainButton = new JButton("Validate Chain");

        transactionsDisplay = new JTextArea(20, 50);
        transactionsDisplay.setEditable(false);

        blockchainDisplay = new JTextArea(20, 50);
        blockchainDisplay.setEditable(false);

        frame.add(new JLabel("Sender:"));
        frame.add(senderField);
        frame.add(new JLabel("Recipient:"));
        frame.add(recipientField);
        frame.add(new JLabel("Amount:"));
        frame.add(amountField);
        frame.add(addTransactionButton);
        frame.add(addBlockButton);
        frame.add(validateChainButton);
        frame.add(new JScrollPane(transactionsDisplay));
        frame.add(new JScrollPane(blockchainDisplay));

        validateChainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (blockchain.isChainValid()) {
                    JOptionPane.showMessageDialog(frame, "The chain is valid");
                } else {
                    JOptionPane.showMessageDialog(frame, "The chain is not valid");
                }
            }
        });


        addTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sender = senderField.getText();
                String recipient = recipientField.getText();
                double amount = Double.parseDouble(amountField.getText());

                Transaction transaction = new Transaction(sender, recipient, amount);
                currentTransactions.add(transaction);

                senderField.setText("");
                recipientField.setText("");
                amountField.setText("");

                updateTransactionsDisplay();
            }
        });

        addBlockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Block newBlock = new Block(blockchain.getLatestBlock().calculateHash(), currentTransactions);
                blockchain.addBlock(newBlock);
                currentTransactions = new ArrayList<>();
                updateBlockchainDisplay();
                updateTransactionsDisplay();
            }
        });

        frame.setVisible(true);
    }

    private void updateBlockchainDisplay() {
        StringBuilder display = new StringBuilder();
        for (Block block : blockchain.getChain()) {
            display.append(block.toString()).append("\n");
        }
        blockchainDisplay.setText(display.toString());
    }

    private void updateTransactionsDisplay() {
        StringBuilder display = new StringBuilder();
        for (Transaction transaction : currentTransactions) {
            display.append(transaction.toString()).append("\n");
        }
        transactionsDisplay.setText(display.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BlockchainGUI();
            }
        });
    }
}