package Lab3;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;

import static Lab3.TradeSystem.users;

class AdminWindow extends JFrame{

    private JButton addButton = new JButton("Добавить участника");
    private JButton modButton = new JButton("Изменить инфорацию участников");
    private JButton resButton = new JButton("Выслать результаты участникам");
    private GridBagLayout gbl = new GridBagLayout();

    AdminWindow () {
        this.setTitle("Администратор");
        this.setSize(300, 200);
        this.setResizable(false);
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize(), fSize = getSize ();
        if (fSize.height > sSize.height) {fSize.height = sSize.height;}
        if (fSize.width  > sSize.width)  {fSize.width = sSize.width;}
        setLocation ((sSize.width - fSize.width)/2, (sSize.height - fSize.height)/3);

        addWindowListener(new CloseEventListener());

        Container container = this.getContentPane();
        container.setLayout(gbl);

        GridBagConstraints lookCon = new GridBagConstraints();

        lookCon.gridx = 0;
        lookCon.gridy = GridBagConstraints.RELATIVE;
        lookCon.fill = GridBagConstraints.BOTH;
        lookCon.insets = new Insets(10, 0, 0, 0);

        addButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        addButton.setHorizontalAlignment(JTextField.CENTER);
        resButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        resButton.setHorizontalAlignment(JTextField.CENTER);
        modButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        modButton.setHorizontalAlignment(JTextField.CENTER);

        resButton.addActionListener(new ResultEventListener());
        addButton.addActionListener(new AddUserEventListener());
        modButton.addActionListener(new ModifyEventListener());
        gbl.setConstraints(modButton, lookCon);
        gbl.setConstraints(resButton, lookCon);
        gbl.setConstraints(addButton, lookCon);

        container.add(resButton);
        container.add(addButton);
        container.add(modButton);
        this.setVisible(true);
    }

    private static class TableInfoWindow extends JFrame {

        private static JTable table = new JTable(new InfoTableModel());

        TableInfoWindow() {
            this.setTitle("Информация о пользователях");
            this.setSize(350, 200);
            this.setResizable(false);
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize(), fSize = getSize();
            if (fSize.height > sSize.height) { fSize.height = sSize.height; }
            if (fSize.width > sSize.width) { fSize.width = sSize.width; }
            setLocation((sSize.width - fSize.width) / 2, (sSize.height - fSize.height) / 3);

            Container container = this.getContentPane();
            container.add(table);

            this.setVisible(true);
        }
    }

    private static class AddUserWindow extends JFrame {

        private GridBagLayout gbl = new GridBagLayout();
        static JTextField loginInput = new JTextField("Логин");
        static JTextField passwordInput = new JTextField("Пароль");
        static JTextField nameInput = new JTextField("Имя");
        private JButton addButton = new JButton("Зарегестрировать пользователя");
        private GridBagConstraints firstInput = new GridBagConstraints();
        private GridBagConstraints otherInput = new GridBagConstraints();
        private GridBagConstraints button = new GridBagConstraints();

        AddUserWindow() {
            this.setTitle("Добавление пользвателя в систему");
            this.setSize(400, 200);
            Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize(), fSize = getSize ();
            if (fSize.height > sSize.height) {fSize.height = sSize.height;}
            if (fSize.width  > sSize.width)  {fSize.width = sSize.width;}
            setLocation ((sSize.width - fSize.width)/3, (sSize.height - fSize.height)/3);
            this.addWindowListener(new UserCloseEventListener());

            Container container = this.getContentPane();
            container.setLayout(gbl);

            firstInput.gridx = 0;
            firstInput.gridy = 0;
            firstInput.fill = GridBagConstraints.BOTH;
            firstInput.insets = new Insets(0, 0, 10, 0);
            otherInput.gridx = 0;
            otherInput.gridy = GridBagConstraints.RELATIVE;
            otherInput.fill = GridBagConstraints.BOTH;
            otherInput.insets = new Insets(10, 0, 10, 0);
            button.gridx = 0;
            button.gridy = GridBagConstraints.RELATIVE;
            button.insets = new Insets(10, 10, 10, 10);

            loginInput.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            loginInput.setHorizontalAlignment(JTextField.LEFT);
            passwordInput.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            passwordInput.setHorizontalAlignment(JTextField.LEFT);
            nameInput.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            nameInput.setHorizontalAlignment(JTextField.LEFT);
            addButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            addButton.setHorizontalAlignment(JTextField.CENTER);

            gbl.setConstraints(nameInput, firstInput);
            gbl.setConstraints(loginInput, otherInput);
            gbl.setConstraints(passwordInput, otherInput);
            gbl.setConstraints(addButton, button);

            container.add(nameInput);
            container.add(loginInput);
            container.add(passwordInput);
            addButton.addActionListener(new AddUserButtonListener());
            container.add(addButton);
            this.setVisible(true);
        }
    }


    private static class InfoTableModel extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return users.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

//        @Override
//        public String getColumnName(int columnIndex) {
//            String result = "";
//            switch (columnIndex) {
//                case 0:
//                    result = "Имя";
//                    break;
//                case 1:
//                    result = "Логин";
//                    break;
//                case 2:
//                    result = "Пароль";
//                    break;
//            }
//            return result;
//        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return users.get(rowIndex).name;
                case 1:
                    return users.get(rowIndex).login;
                case 2:
                    return users.get(rowIndex).password;
                default:
                    return "";
            }
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            //TODO: Изменение текста в ячейках и users, перезапись Users.bin
        }
    }

    private static class AddUserButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String login = AddUserWindow.loginInput.getText();
            if (TradeSystem.findUser(login) == null) {
                TradeSystem.addUser(AddUserWindow.nameInput.getText(), login, AddUserWindow.passwordInput.getText());
                ObjectOutputStream out;
                try {
                    out = new ObjectOutputStream(new FileOutputStream("Users.bin"));
                    for (User user1 : users)
                        out.writeObject(user1);
                    out.close();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
            else
                JOptionPane.showMessageDialog(null, "Такой пользователь уже зарегестрирован!",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class ModifyEventListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            new TableInfoWindow();
        }
    }

    private static class ResultEventListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            TradeSystem.processRequests();
            JOptionPane.showMessageDialog(null, "Информация по заявкам рассчитана и выслана пользователям",
                    "", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static class AddUserEventListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            new AddUserWindow();
        }
    }

    private static class UserCloseEventListener implements WindowListener {

        public void windowOpened(WindowEvent e) {}

        public void windowClosing(WindowEvent e) {
            AddUserWindow.nameInput.setText("Имя");
            AddUserWindow.loginInput.setText("Логин");
            AddUserWindow.passwordInput.setText("Пароль");
        }

        public void windowClosed(WindowEvent e) {}

        public void windowIconified(WindowEvent e) {}

        public void windowDeiconified(WindowEvent e) {}

        public void windowActivated(WindowEvent e) {}

        public void windowDeactivated(WindowEvent e) {}
    }

    static class CloseEventListener implements WindowListener {

        public void windowOpened(WindowEvent e) {}

        public void windowClosing(WindowEvent e) {
            new InitialWindow();
        }

        public void windowClosed(WindowEvent e) {}

        public void windowIconified(WindowEvent e) {}

        public void windowDeiconified(WindowEvent e) {}

        public void windowActivated(WindowEvent e) {}

        public void windowDeactivated(WindowEvent e) {}
    }
}