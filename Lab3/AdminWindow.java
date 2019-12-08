package Lab3;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.table.*;

import static Lab3.TradeSystem.users;

class AdminWindow extends JFrame{

    private JButton addButton = new JButton("Добавить участника");
    private JButton lookButton = new JButton("Просмотреть инфорацию участников");
    private JButton modButton = new JButton("Изменить информацию участников");
    private JButton resButton = new JButton("Выслать результаты участникам");
    private GridBagLayout gbl = new GridBagLayout();
    private GridBagConstraints lookCon = new GridBagConstraints();

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

        lookCon.gridx = 0;
        lookCon.gridy = GridBagConstraints.RELATIVE;
        lookCon.fill = GridBagConstraints.BOTH;
        lookCon.insets = new Insets(10, 0, 0, 0);

        addButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        addButton.setHorizontalAlignment(JTextField.CENTER);
        resButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        resButton.setHorizontalAlignment(JTextField.CENTER);
        lookButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lookButton.setHorizontalAlignment(JTextField.CENTER);
        modButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        modButton.setHorizontalAlignment(JTextField.CENTER);

        resButton.addActionListener(new ResultEventListener());
        addButton.addActionListener(e -> new AddUserWindow());
        lookButton.addActionListener(e -> new TableInfoWindow());
        modButton.addActionListener(e -> new ModUserWindow());
        gbl.setConstraints(lookButton, lookCon);
        gbl.setConstraints(resButton, lookCon);
        gbl.setConstraints(addButton, lookCon);
        gbl.setConstraints(modButton, lookCon);

        container.add(resButton);
        container.add(addButton);
        container.add(lookButton);
        container.add(modButton);
        this.setVisible(true);
    }

    private static class TableInfoWindow extends JFrame {

        private JTable table = new JTable(new InfoTableModel());

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

        GridBagLayout gbl = new GridBagLayout();
        static JTextField loginInput = new JTextField("Логин");
        static JTextField passwordInput = new JTextField("Пароль");
        static JTextField nameInput = new JTextField("Имя");
        protected JButton addButton = new JButton("Зарегестрировать пользователя");
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
            this.setResizable(false);

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

    private static class ModUserWindow extends AddUserWindow {

        String[] userLogins = new String[users.size()];
        private JPanel leftPane = new JPanel();
        private JPanel rightPane = new JPanel();

        ModUserWindow() {
            convertUsersToString();

            this.setTitle("Изменение информации о пользователях");
            this.setSize(390, 200);

            JComboBox<String> userList = new JComboBox<>(userLogins);
            //userList.addItemListener();
            Container container = this.getContentPane();
            container.setLayout(new FlowLayout(FlowLayout.CENTER));
            leftPane.setLayout(new FlowLayout(FlowLayout.CENTER));
            rightPane.setLayout(gbl);

            addButton.setText("Завершить изменения");
            addButton.addActionListener(new ModifyEventListener());
            leftPane.add(new JLabel("Выберите пользователя"));
            leftPane.add(userList);
            rightPane.add(nameInput);
            rightPane.add(loginInput);
            rightPane.add(passwordInput);
            rightPane.add(addButton);

            container.add(leftPane);
            container.add(rightPane);
        }

        void convertUsersToString () {
            for (int i = 0; i < users.size(); i++) {
                userLogins[i] = users.get(i).name;
            }
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

        @Override
        public void actionPerformed(ActionEvent e) {
//            ObjectOutputStream out;
//            try {
//                out = new ObjectOutputStream(new FileOutputStream("Users.bin"));
//                for (User user1 : users)
//                    out.writeObject(user1);
//                out.close();
//            } catch (IOException ex) {
//                System.err.println(ex.getMessage());
//            }
        }
    }

    private static class ItemChangedListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            //TODO: В случае смены пользователя изменить значения текстовых полей
        }
    }

    private static class ResultEventListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            TradeSystem.processRequests();
            JOptionPane.showMessageDialog(null, "Информация по заявкам рассчитана и выслана пользователям",
                    "", JOptionPane.INFORMATION_MESSAGE);
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