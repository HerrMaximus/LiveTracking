package de.swm.livetracking

import de.swm.gui.GUI
import org.junit.jupiter.api.Test
import javax.swing.SwingUtilities

class GUITest {
    @Test
    fun `Create the UI`() {
        SwingUtilities.invokeLater {
            GUI()
        }
    }
}