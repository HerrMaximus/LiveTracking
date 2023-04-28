package de.swm.gui


import com.formdev.flatlaf.FlatDarculaLaf
import java.awt.GridBagConstraints
import java.awt.GridLayout
import javax.swing.*

class GUI {
    val username = JTextField()
    val message = JTextField()
    val listOfMessages = JList<String>()
class GUI : JFrame() {

    private val usernameField = JTextField()
    private val messageField = JTextField("Enter your message")
    private var messageList = JList<String>()

    init {
        createUI()
    }








    }
}