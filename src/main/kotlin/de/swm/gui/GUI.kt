package de.swm.gui

import de.swm.websocket.Websocket
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import javax.swing.*

class GUI : JFrame("MQTT Client") {
    private val usernameTextField = JTextField()
    private val passwordTextField = JTextField()
    private val connectionStatusLabel = JLabel("Disconnected")
    private val messageTextField = JTextField()
    private val model = DefaultListModel<String>()
    private val logList = JList(model)
    private var loginStatus: Boolean = false

    init {
        createGUI()
    }

    private fun createGUI() {
        defaultCloseOperation = EXIT_ON_CLOSE
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

        usernameTextField.alignmentX = CENTER_ALIGNMENT
        topPanel.add(usernameTextField)

        val passwordLabel = JLabel("Password:")
        passwordLabel.alignmentX = CENTER_ALIGNMENT
        topPanel.add(passwordLabel)

        passwordTextField.alignmentX = CENTER_ALIGNMENT
        topPanel.add(passwordTextField)

        val connectPanel = JPanel()
        connectPanel.layout = FlowLayout(FlowLayout.CENTER)
        connectPanel.alignmentX = CENTER_ALIGNMENT
        topPanel.add(connectPanel)

        val connectButton = JButton("Connect")
        connectButton.addActionListener { connectButtonClicked() }
        connectPanel.add(connectButton)

        val statusLabel = JLabel("Status: ")
        statusLabel.alignmentX = CENTER_ALIGNMENT
        connectPanel.add(statusLabel)

        connectionStatusLabel.foreground = Color.red
        connectionStatusLabel.alignmentX = CENTER_ALIGNMENT
        connectPanel.add(connectionStatusLabel)

        //Middle area
        val middlePanel = JPanel()
        middlePanel.layout = BoxLayout(middlePanel, BoxLayout.Y_AXIS)
        add(middlePanel, BorderLayout.CENTER)

        val messageLabel = JLabel("Message:")
        messageLabel.alignmentX = CENTER_ALIGNMENT
        middlePanel.add(messageLabel)

        messageTextField.preferredSize = Dimension(100, 20)
        messageTextField.alignmentX = CENTER_ALIGNMENT
        middlePanel.add(messageTextField)

        val sendButton = JButton("Send")
        sendButton.alignmentX = CENTER_ALIGNMENT
        sendButton.addActionListener { sendButtonClicked() }
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

        logList.selectionMode = ListSelectionModel.SINGLE_SELECTION

        val scrollPane = JScrollPane(logList)
        logPanel.add(scrollPane)

        isVisible = true
    }

    private fun connectButtonClicked() {
        loginStatus = Websocket(usernameTextField.text, passwordTextField.text).login() //Return boolean if login was successfully or not
        if (loginStatus) {
            connectionStatusLabel.text = "Connected"
            connectionStatusLabel.foreground = Color.green
        }
    }

    private fun sendButtonClicked() {
        if (loginStatus) WebSocketClient().sendMessage(usernameTextField.text, passwordTextField.text, messageTextField.text)
        else messageTextField.text = "You are not connected!"
    }
}