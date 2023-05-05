package de.swm.gui

import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.FlowLayout
import javax.swing.*

class GUI : JFrame("MQTT Client") {
    init {
        createGUI()
    }

    fun createGUI() {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        size = Dimension(400, 600)
        setLocationRelativeTo(null) //Center from the monitor
        layout = BorderLayout(10, 10)

        //Upper area
        val topPanel = JPanel()
        topPanel.layout = BoxLayout(topPanel, BoxLayout.Y_AXIS)
        add(topPanel, BorderLayout.NORTH)

        val usernameLabel = JLabel("Username:")
        usernameLabel.alignmentX = CENTER_ALIGNMENT
        topPanel.add(usernameLabel)

        val usernameTextField = JTextField("")
        usernameTextField.alignmentX = CENTER_ALIGNMENT
        topPanel.add(usernameTextField)

        val passwordLabel = JLabel("Password:")
        passwordLabel.alignmentX = CENTER_ALIGNMENT
        topPanel.add(passwordLabel)

        val passwordTextField = JTextField("")
        passwordTextField.alignmentX = CENTER_ALIGNMENT
        topPanel.add(passwordTextField)

        val connectPanel = JPanel()
        connectPanel.layout = FlowLayout(FlowLayout.CENTER)
        connectPanel.alignmentX = CENTER_ALIGNMENT
        topPanel.add(connectPanel)

        val connectButton = JButton("Connect")
        connectButton.addActionListener { connectButtonClicked(usernameTextField.text, passwordTextField.text) }
        connectPanel.add(connectButton)

        val statusLabel = JLabel("Status: ")
        statusLabel.alignmentX = CENTER_ALIGNMENT
        connectPanel.add(statusLabel)

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