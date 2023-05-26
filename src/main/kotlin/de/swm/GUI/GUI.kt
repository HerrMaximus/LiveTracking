package de.swm.gui

import de.swm.websocket.Websocket
import java.awt.*
import java.text.SimpleDateFormat
import java.util.*
import javax.swing.*
import javax.swing.border.EmptyBorder

data class DropdownItem(val label: String, val id: String) {
    override fun toString() = label
    override fun equals(other: Any?) = other is DropdownItem && other.id == id
    override fun hashCode() = id.hashCode()
}

class GUI : JFrame("MQTT Client") {
    private val usernameTextField = JTextField()
    private val passwordPasswordField = JPasswordField()
    private val connectionStatusLabel = JLabel("Disconnected")
    private val messageTextField = JTextField()
    private val model = DefaultListModel<String>()
    private val logList = JList(model)
    private var loginStatus: Boolean = false

    companion object {
        val dropdown = JComboBox<DropdownItem>()
    }

    init {
        createGUI()
    }

    private fun createGUI() {
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(400, 600)
        setLocationRelativeTo(null) // Center from the monitor
        layout = BorderLayout(10, 10)

        // Upper area
        val topPanel = JPanel()
        topPanel.layout = BoxLayout(topPanel, BoxLayout.Y_AXIS)
        add(topPanel, BorderLayout.NORTH)

        val usernameLabel = JLabel("Username:")
        usernameLabel.alignmentX = Component.CENTER_ALIGNMENT
        topPanel.add(usernameLabel)

        usernameTextField.alignmentX = Component.CENTER_ALIGNMENT
        topPanel.add(usernameTextField)

        val passwordLabel = JLabel("Password:")
        passwordLabel.alignmentX = Component.CENTER_ALIGNMENT
        topPanel.add(passwordLabel)

        passwordPasswordField.alignmentX = Component.CENTER_ALIGNMENT
        topPanel.add(passwordPasswordField)

        val connectPanel = JPanel()
        connectPanel.layout = FlowLayout(FlowLayout.CENTER)
        connectPanel.alignmentX = Component.CENTER_ALIGNMENT
        topPanel.add(connectPanel)

        dropdown.alignmentX = Component.LEFT_ALIGNMENT
        val dropdownItems = arrayOf(
            DropdownItem("Europa", "EU"),
            DropdownItem("America", "US")
        )
        val model = DefaultComboBoxModel(dropdownItems)
        dropdown.model = model
        connectPanel.add(dropdown)

        val connectButton = JButton("Connect")
        connectButton.addActionListener { connectButtonClicked() }
        connectPanel.add(connectButton)

        val statusLabel = JLabel("Status: ")
        statusLabel.alignmentX = Component.CENTER_ALIGNMENT
        connectPanel.add(statusLabel)

        connectionStatusLabel.foreground = Color.red
        connectionStatusLabel.alignmentX = Component.CENTER_ALIGNMENT
        connectPanel.add(connectionStatusLabel)

        // Middle area
        val middlePanel = JPanel()
        middlePanel.layout = BoxLayout(middlePanel, BoxLayout.Y_AXIS)
        middlePanel.alignmentX = Component.CENTER_ALIGNMENT
        middlePanel.border = EmptyBorder(20, 0, 20, 0) // Increased spacing from connectPanel
        add(middlePanel, BorderLayout.CENTER)

        val messageLabel = JLabel("Message:")
        messageLabel.alignmentX = Component.CENTER_ALIGNMENT
        middlePanel.add(messageLabel)

        val messagePanel = JPanel()
        messagePanel.layout = BoxLayout(messagePanel, BoxLayout.X_AXIS)
        messagePanel.alignmentX = Component.CENTER_ALIGNMENT
        middlePanel.add(messagePanel)

        messageTextField.preferredSize = Dimension(100, messageTextField.preferredSize.height) // Reducing the width
        messageTextField.alignmentX = Component.LEFT_ALIGNMENT
        messagePanel.add(messageTextField)

        val sendButton = JButton("Send")
        sendButton.alignmentX = Component.CENTER_ALIGNMENT
        sendButton.addActionListener { sendButtonClicked() }

        val sendButtonPanel = JPanel()
        sendButtonPanel.layout = BoxLayout(sendButtonPanel, BoxLayout.X_AXIS)
        sendButtonPanel.alignmentX = Component.CENTER_ALIGNMENT
        sendButtonPanel.add(sendButton)
        middlePanel.add(sendButtonPanel)

        // Lower area
        val logPanel = JPanel()
        logPanel.layout = BoxLayout(logPanel, BoxLayout.Y_AXIS)
        logPanel.preferredSize = Dimension(0, 300) // Increasing the height
        add(logPanel, BorderLayout.SOUTH)

        val logLabel = JLabel("Log:")
        logLabel.alignmentX = Component.CENTER_ALIGNMENT
        logPanel.add(logLabel)

        logList.selectionMode = ListSelectionModel.SINGLE_SELECTION

        val scrollPane = JScrollPane(logList)
        logPanel.add(scrollPane)

        isVisible = true
    }

    private fun connectButtonClicked() {
        loginStatus = Websocket(usernameTextField.text, String(passwordPasswordField.password)).login() //Return boolean if login was successfully or not
        if (loginStatus) {
            connectionStatusLabel.text = "Connected"
            connectionStatusLabel.foreground = Color.green
        }

        val websocket = Websocket(usernameTextField.text, String(passwordPasswordField.password))
        websocket.addListener {
            val date = Date(it.time!!)
            val format = SimpleDateFormat("hh:mm:s")
            addMessagesToLog("${it.username}: ${it.message} (${format.format(date)})")
        }
    }

    private fun sendButtonClicked() {
        if (loginStatus) Websocket(usernameTextField.text, String(passwordPasswordField.password)).send(messageTextField.text) //Send message if login was successfully
        else messageTextField.text = "You are not connected!"
    }

    private fun addMessagesToLog(message: String) {
        model.addElement(message) //Add messages to logList
    }
}