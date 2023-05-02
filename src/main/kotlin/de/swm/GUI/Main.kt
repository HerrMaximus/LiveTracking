package de.swm.gui

import com.formdev.flatlaf.FlatDarculaLaf
import javax.swing.JDialog
import javax.swing.JFrame
import javax.swing.UIManager

fun main() {
    //Important for dark mode
    UIManager.setLookAndFeel(FlatDarculaLaf())
    JFrame.setDefaultLookAndFeelDecorated(true)
    JDialog.setDefaultLookAndFeelDecorated(true)
    UIManager.put("ScrollBar.showButtons", true)
    UIManager.put("ScrollBar.width", 16)
    UIManager.put("TabbedPane.showTabSeparators", true)
    
    GUI()
}