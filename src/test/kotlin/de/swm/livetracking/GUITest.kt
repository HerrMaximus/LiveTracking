package de.swm.livetracking

import de.swm.GUI.GUI
import org.junit.jupiter.api.Test
import javax.swing.SwingUtilities

class GUITest {
    @Test
    fun `Create and build the GUI`() {
        SwingUtilities.invokeLater {
            GUI()
        }
    }
}