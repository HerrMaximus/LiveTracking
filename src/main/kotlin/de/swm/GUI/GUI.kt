package de.swm.gui

import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.ActionListener
import javax.swing.*

class GUI : JFrame("MQTT Client") {
    init {
        createGUI()
    }


        val topPanel = JPanel()
        topPanel.layout = BoxLayout(topPanel, BoxLayout.PAGE_AXIS)
        topPanel.layout = BoxLayout(topPanel, BoxLayout.Y_AXIS)
        add(topPanel, BorderLayout.NORTH)

        // Label for username
        val usernameLabel = JLabel("Your username")
        val usernameLabel = JLabel("Username:")
        usernameLabel.alignmentX = CENTER_ALIGNMENT
        topPanel.add(usernameLabel)

        // Text field for username
        usernameField.preferredSize = Dimension(200, 30)
        topPanel.add(usernameField)
        val usernameTextField = JTextField("")
        usernameTextField.alignmentX = CENTER_ALIGNMENT
        topPanel.add(usernameTextField)

        // Text field for message
        messageField.preferredSize = Dimension(200, 30)
        topPanel.add(messageField)
        val passwordLabel = JLabel("Password:")
        passwordLabel.alignmentX = CENTER_ALIGNMENT
        topPanel.add(passwordLabel)

        // Button to send message
        val sendButton = JButton("Send message")
        sendButton.addActionListener(sendMessageListener)
        topPanel.add(sendButton)
        val passwordTextField = JTextField("")
        passwordTextField.alignmentX = CENTER_ALIGNMENT
        topPanel.add(passwordTextField)

        // Panel for the bottom section of the GUI
        val bottomPanel = JPanel()
        bottomPanel.layout = BorderLayout()
        val connectPanel = JPanel()
        connectPanel.layout = FlowLayout(FlowLayout.CENTER)
        connectPanel.alignmentX = CENTER_ALIGNMENT
        topPanel.add(connectPanel)

        // List for displaying messages
        messageList.preferredSize = Dimension(450, 400)
        messageList.selectionMode = ListSelectionModel.SINGLE_SELECTION
        val connectButton = JButton("Connect")
        connectButton.addActionListener { connectButtonClicked(usernameTextField.text, passwordTextField.text) }
        connectPanel.add(connectButton)

        // Scroll pane for the message list
        val scrollPane = JScrollPane(messageList)
        bottomPanel.add(scrollPane, BorderLayout.CENTER)
        val statusLabel = JLabel("Status: ")
        statusLabel.alignmentX = CENTER_ALIGNMENT
        connectPanel.add(statusLabel)

        // Add the top and bottom panels to the main frame
        add(topPanel, BorderLayout.NORTH)
        add(bottomPanel, BorderLayout.SOUTH)
        val connectionStatusLabel = JLabel("Disconnected")
        connectionStatusLabel.alignmentX = CENTER_ALIGNMENT
        connectPanel.add(connectionStatusLabel)

        //Middle area
        val middlePanel = JPanel()
        middlePanel.layout = BoxLayout(middlePanel, BoxLayout.Y_AXIS)
        add(middlePanel, BorderLayout.CENTER)

        val messageLabel = JLabel("Message:")
        messageLabel.alignmentX = CENTER_ALIGNMENT
        middlePanel.add(messageLabel)

        val messageTextField = JTextField()
        messageTextField.preferredSize = Dimension(100, 20)
        messageTextField.alignmentX = CENTER_ALIGNMENT
        middlePanel.add(messageTextField)

        val sendButton = JButton("Send")
        sendButton.alignmentX = CENTER_ALIGNMENT
        sendButton.addActionListener { sendButtonClicked(messageTextField.text) }
        middlePanel.add(sendButton)

        //Lower area
        val logPanel = JPanel()
        logPanel.layout = BoxLayout(logPanel, BoxLayout.Y_AXIS)
        logPanel.preferredSize = Dimension(0, 100)
        add(logPanel, BorderLayout.SOUTH)

        val logLabel = JLabel("Log:")
        logLabel.alignmentX = CENTER_ALIGNMENT
        logPanel.add(Box.createVerticalGlue())
        logPanel.add(logLabel)
        logPanel.add(Box.createVerticalGlue())

        val logList = JList<String>()
        logList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION)

        val scrollPane = JScrollPane(logList) //TODO: Not working, it isnt visible
        logPanel.add(scrollPane)

        isVisible = true
    }

    private fun connectButtonClicked(username: String, password: String) {
        //TODO: Send username/password to Sven + update Status if connection works
    }

    private fun sendButtonClicked(message: String) {
        //TODO: Send message
    }
}