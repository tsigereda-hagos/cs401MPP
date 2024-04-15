package com.team2.mppproject.librarysystem;

import com.team2.mppproject.business.SystemController;
import com.team2.mppproject.dataaccess.Auth;
import com.team2.mppproject.useCases.ControllerInterface;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

public class LibrarySystem extends JFrame implements LibWindow {

    ControllerInterface ci = new SystemController();
    public final static LibrarySystem INSTANCE = new LibrarySystem();
    JPanel mainPanel;
    JMenuBar menuBar;
    JMenu options;
    JMenu memberOptions;
    JMenu bookOptions;
    JMenuItem login, logout, allBookIds, allMemberIds, addMember, addBookCopy, addBook, overdue, checkOut, checkOutRecord;
    String pathToImage;
    private boolean isInitialized = false;

    private static LibWindow[] allWindows = {
        AddMemberWindow.INSTANCE,
        AddBookCopyWindow.INSTANCE,
        LibrarySystem.INSTANCE,
        AddBookWindow.INSTANCE,
        LoginWindow.INSTANCE,
        AllMemberIdsWindow.INSTANCE,
        CheckOutRecordWindow.INSTANCE,
        OverdueWindow.INSTANCE,
        CheckOutBookWindow.INSTANCE,
        AllBookIdsWindow.INSTANCE};

    public static void hideAllWindows() {
        for (LibWindow frame : allWindows) {
            frame.setVisible(false);
        }
    }

    private LibrarySystem() {
    }

    public void init() {
        formatContentPane();
        setPathToImage();
        insertSplashImage();

        createMenus();
        //pack();
        setSize(660, 500);
        isInitialized = true;
    }

    private void formatContentPane() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));
        getContentPane().add(mainPanel);
    }

    private void setPathToImage() {
        String currDirectory = System.getProperty("user.dir");
        pathToImage = currDirectory + "/src/main/java/com/team2/mppproject/librarysystem/library.png";
    }

    private void insertSplashImage() {
        ImageIcon image = new ImageIcon(pathToImage);
        mainPanel.add(new JLabel(image));
    }

    private void createMenus() {
        menuBar = new JMenuBar();
        addMenuItems();
        menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
        setJMenuBar(menuBar);
    }

    public void addMenuItems() {
        menuBar.removeAll();

        SystemController controller = (SystemController) ci;

        if (controller.currentAuth == null) {
            options = new JMenu("Options");

            login = new JMenuItem("Login");
            login.addActionListener(new LoginListener());

            options.add(login);
            menuBar.add(options);
        } else {
            options = new JMenu("Options");

            logout = new JMenuItem("Logout");
            logout.addActionListener(new LogoutListener());

            options.add(logout);
            menuBar.add(options);

            if (controller.currentAuth == Auth.ADMIN || controller.currentAuth == Auth.BOTH) {
                memberOptions = new JMenu("Member");
                menuBar.add(memberOptions);

                allMemberIds = new JMenuItem("All Member Ids");
                allMemberIds.addActionListener(new AllMemberIdsListener());

                addMember = new JMenuItem("Add Member");
                addMember.addActionListener(new AddMemberListener());

                memberOptions.add(allMemberIds);
                memberOptions.add(addMember);
            }

            if (controller.currentAuth == Auth.LIBRARIAN || controller.currentAuth == Auth.BOTH) {
                bookOptions = new JMenu("Book");
                menuBar.add(bookOptions);

                allBookIds = new JMenuItem("All Book Ids");
                allBookIds.addActionListener(new AllBookIdsListener());

                addBookCopy = new JMenuItem("Add Book Copy");
                addBookCopy.addActionListener(new AddBookCopyListener());

                addBook = new JMenuItem("Add Book");
                addBook.addActionListener(new AddBookListener());

                overdue = new JMenuItem("List Overdue");
                overdue.addActionListener(new OverdueListener());

                checkOut = new JMenuItem("Checkout");
                checkOut.addActionListener(new CheckoutListener());
                
                checkOutRecord = new JMenuItem("Checkout Record");
                checkOutRecord.addActionListener(new CheckoutRecordListener());

                bookOptions.add(allBookIds);
                bookOptions.add(addBookCopy);
                bookOptions.add(addBook);
                bookOptions.add(overdue);
                bookOptions.add(checkOut);
                bookOptions.add(checkOutRecord);
            }
        }
    }

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();

            if (!LoginWindow.INSTANCE.isInitialized()) {
                LoginWindow.INSTANCE.init();
                Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
                LoginWindow.INSTANCE.isInitialized(true);
            }

            LoginWindow.INSTANCE.setVisible(true);
        }
    }

    class LogoutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ci.logout();
            addMenuItems();
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        }
    }

    class AllBookIdsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            AllBookIdsWindow.INSTANCE.init();

            List<String> ids = ci.allBookIds();
            Collections.sort(ids);
            StringBuilder sb = new StringBuilder();
            for (String s : ids) {
                sb.append(s + "\n");
            }
            AllBookIdsWindow.INSTANCE.setData(sb.toString());
            AllBookIdsWindow.INSTANCE.pack();
            //AllBookIdsWindow.INSTANCE.setSize(660,500);
            Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
            AllBookIdsWindow.INSTANCE.setVisible(true);

        }

    }

    class AllMemberIdsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            AllMemberIdsWindow.INSTANCE.init();
            AllMemberIdsWindow.INSTANCE.pack();
            AllMemberIdsWindow.INSTANCE.setVisible(true);

            LibrarySystem.hideAllWindows();
            AllBookIdsWindow.INSTANCE.init();

            List<String> ids = ci.allMemberIds();
            Collections.sort(ids);
            StringBuilder sb = new StringBuilder();
            for (String s : ids) {
                sb.append(s + "\n");
            }
            AllMemberIdsWindow.INSTANCE.setData(sb.toString());
            AllMemberIdsWindow.INSTANCE.pack();
            //AllMemberIdsWindow.INSTANCE.setSize(660,500);
            Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
            AllMemberIdsWindow.INSTANCE.setVisible(true);
        }

    }

    class AddMemberListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();

            if (!AddMemberWindow.INSTANCE.isInitialized()) {
                AddMemberWindow.INSTANCE.isInitialized(true);
                AddMemberWindow.INSTANCE.setTitle("Add new member");
                AddMemberWindow.INSTANCE.init();

                Util.centerFrameOnDesktop(AddMemberWindow.INSTANCE);
            }

            AddMemberWindow.INSTANCE.setVisible(true);
        }

    }

    class AddBookCopyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();

            if (!AddBookCopyWindow.INSTANCE.isInitialized()) {
                AddBookCopyWindow.INSTANCE.isInitialized(true);
                AddBookCopyWindow.INSTANCE.setTitle("Add book copy");
                AddBookCopyWindow.INSTANCE.init();

                Util.centerFrameOnDesktop(AddBookCopyWindow.INSTANCE);
            }

            AddBookCopyWindow.INSTANCE.setVisible(true);
        }

    }

    class OverdueListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();

            if (!OverdueWindow.INSTANCE.isInitialized()) {
                OverdueWindow.INSTANCE.isInitialized(true);
                OverdueWindow.INSTANCE.setTitle("List overdue");
                Util.centerFrameOnDesktop(OverdueWindow.INSTANCE);
            }

            OverdueWindow.INSTANCE.init();
            OverdueWindow.INSTANCE.setVisible(true);
        }
    }

    class AddBookListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();

            if (!AddBookWindow.INSTANCE.isInitialized()) {
                AddBookWindow.INSTANCE.isInitialized(true);
                AddBookWindow.INSTANCE.setTitle("Add book");
                AddBookWindow.INSTANCE.init();

                Util.centerFrameOnDesktop(AddBookWindow.INSTANCE);
            }

            AddBookWindow.INSTANCE.setVisible(true);
        }
    }

    class CheckoutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();

            if (!CheckOutBookWindow.INSTANCE.isInitialized()) {
                CheckOutBookWindow.INSTANCE.isInitialized(true);
                CheckOutBookWindow.INSTANCE.setTitle("Checkout");
                CheckOutBookWindow.INSTANCE.init();

                Util.centerFrameOnDesktop(CheckOutBookWindow.INSTANCE);
            }

            CheckOutBookWindow.INSTANCE.setVisible(true);
        }
    }
    

    class CheckoutRecordListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();

            if (!CheckOutRecordWindow.INSTANCE.isInitialized()) {
                CheckOutRecordWindow.INSTANCE.isInitialized(true);
                CheckOutRecordWindow.INSTANCE.setTitle("Checkout records");
                CheckOutRecordWindow.INSTANCE.init();

                Util.centerFrameOnDesktop(CheckOutRecordWindow.INSTANCE);
            }

            CheckOutRecordWindow.INSTANCE.setVisible(true);
        }
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;

    }

}
