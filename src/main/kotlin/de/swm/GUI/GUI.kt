package de.swm.gui

import com.formdev.flatlaf.FlatDarculaLaf
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*

class GUI : JFrame() {

    private val usernameField = JTextField()
    private val messageField = JTextField("Enter your message")
    private var messageList = JList<String>()

    init {
        UIManager.setLookAndFeel(FlatDarculaLaf())
        setDefaultLookAndFeelDecorated(true)
        createUI()
    }

    private fun createUI() {
        title = "MQTT-Client"
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(500, 500)
        setLocationRelativeTo(null)

        // Panel for the top section of the GUI
        val topPanel = JPanel()
        topPanel.layout = BoxLayout(topPanel, BoxLayout.PAGE_AXIS)

        // Label for username
        val usernameLabel = JLabel("Your username")
        topPanel.add(usernameLabel)

        // Text field for username
        usernameField.preferredSize = Dimension(200, 30)
        topPanel.add(usernameField)

        // Text field for message
        messageField.preferredSize = Dimension(200, 30)
        topPanel.add(messageField)

        // Button to send message
        val sendButton = JButton("Send message")
        topPanel.add(sendButton)

        // Panel for the bottom section of the GUI
        val bottomPanel = JPanel()
        bottomPanel.layout = BorderLayout()

        // List for displaying messages
        messageList.preferredSize = Dimension(450, 400)
        messageList.selectionMode = ListSelectionModel.SINGLE_SELECTION

        // Scroll pane for the message list
        val scrollPane = JScrollPane(messageList)
        bottomPanel.add(scrollPane, BorderLayout.CENTER)

        // Add the top and bottom panels to the main frame
        add(topPanel, BorderLayout.NORTH)
        add(bottomPanel, BorderLayout.SOUTH)

        isVisible = true
    }
}