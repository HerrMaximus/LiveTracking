package de.swm.GUI


import com.formdev.flatlaf.FlatDarculaLaf
import java.awt.GridBagConstraints
import java.awt.GridLayout
import javax.swing.*

class GUI {
    val username = JTextField()
    val message = JTextField()
    val listOfMessages = JList<String>()

    /*
    - Input für den eigenen Nutzernamen am anfang
    - Input für die Chatnachricht
    - Button zum Absenden dieser Nachricht
    - Liste von allen Chatnachrichten
     */

    init {
        UIManager.setLookAndFeel(FlatDarculaLaf())
        JFrame.setDefaultLookAndFeelDecorated(true)
        buildGUI(createGUI())
    }
    private fun createGUI(): JFrame {
        val frame = JFrame("MQTT-Client")
        frame.setSize(1000, 1000)
        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        frame.layout = GridLayout(2, 1)
        return frame
    }

    private fun buildGUI(frame: JFrame) {
        val c = GridBagConstraints(); c.fill = GridBagConstraints.BOTH

        val usernameLbl = JLabel("Your username:")
        c.gridx = 0; c.gridy = 0
        frame.add(usernameLbl, c)

        c.gridx = 1; c.gridy = 0
        frame.add(username, c)

        c.gridx = 0; c.gridy = 1
        frame.add(message, c)

        val messageBt = JButton("Send message")
        c.gridx = 1; c.gridy = 1
        frame.add(messageBt, c)

        c.gridx = 2; c.gridy = 0
        frame.add(listOfMessages, c)

        val listScroller = JScrollPane(listOfMessages)
        c.gridx = 2; c.gridy = 1
        frame.add(listScroller, c)

        frame.isVisible = true
    }
}