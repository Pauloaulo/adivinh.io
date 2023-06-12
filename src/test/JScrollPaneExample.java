package test;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JScrollPaneExample {
        public static void main(String[] args) {
            JFrame frame = new JFrame("Exemplo de JScrollPane");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);

            // Cria um JPanel para ser usado como conteúdo da JScrollPane
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            // Cria uma JScrollPane com o JPanel como conteúdo
            JScrollPane scrollPane = new JScrollPane(panel);

            // Cria um JButton para adicionar um novo componente ao JPanel
            JButton addButton = new JButton("Adicionar Componente");
            addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JLabel label = new JLabel("Novo Componente");
                    panel.add(label);

                    // Atualiza a JScrollPane
                    scrollPane.revalidate();
                    scrollPane.repaint();
                }
            });

            frame.add(scrollPane, BorderLayout.CENTER);
            frame.add(addButton, BorderLayout.SOUTH);

            frame.setVisible(true);
        }

}
