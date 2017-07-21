package com.mercury.platform.ui.dialog;


import com.mercury.platform.ui.adr.components.panel.ui.IconsListCellRenderer;
import com.mercury.platform.ui.components.fields.font.FontStyle;
import com.mercury.platform.ui.components.panel.VerticalScrollContainer;
import com.mercury.platform.ui.misc.AppThemeColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class IconSelectDialog extends BaseDialog<String,String[]> {
    private JList iconsList;
    public IconSelectDialog(String[] payload) {
        super(null, null, payload);
    }

    @Override
    protected void createView() {
        VerticalScrollContainer container = new VerticalScrollContainer();
        container.setBackground(AppThemeColor.SLIDE_BG);
        container.setLayout(new BorderLayout());

        JScrollPane scrollPane = this.componentsFactory.getVerticalContainer(container);
        this.iconsList = new JList<>(this.payload);
        this.iconsList.setBackground(AppThemeColor.SLIDE_BG);
        this.iconsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.iconsList.setVisibleRowCount(-1);
        this.iconsList.setCellRenderer(new IconsListCellRenderer());
        this.iconsList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        this.iconsList.setPreferredSize(new Dimension(500,260));

        container.add(this.iconsList,BorderLayout.CENTER);

        scrollPane.setBorder(BorderFactory.createLineBorder(AppThemeColor.BORDER_DARK));
        this.add(this.getFilterPanel(),BorderLayout.PAGE_START);
        this.add(scrollPane,BorderLayout.CENTER);
        this.add(this.getBottomPanel(),BorderLayout.PAGE_END);
    }
    public void setSelectedIcon(String iconPath){
        this.iconsList.setSelectedValue(iconPath,true);
    }

    private JPanel getFilterPanel(){
        JPanel root = this.componentsFactory.getJPanel(new BorderLayout());
        root.add(this.componentsFactory.getTextLabel("Filter:"),BorderLayout.LINE_START);
        root.add(this.componentsFactory.getTextField("Filter:", FontStyle.REGULAR,15),BorderLayout.CENTER);
        root.setBackground(AppThemeColor.SLIDE_BG);
        root.setBorder(
                BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppThemeColor.BORDER_DARK),
                        BorderFactory.createEmptyBorder(4,0,4,4)));

        JPanel wrapper = this.componentsFactory.wrapToSlide(root);
        wrapper.setBorder(BorderFactory.createEmptyBorder(4,0,4,0));
        return wrapper;
    }
    private JPanel getBottomPanel(){
        JPanel root = this.componentsFactory.getJPanel(new FlowLayout(FlowLayout.CENTER));
        JButton selectButton = this.componentsFactory.getBorderedButton("Select");
        selectButton.setFont(this.componentsFactory.getFont(FontStyle.BOLD,15f));
        JButton cancelButton =  componentsFactory.getButton(
                FontStyle.BOLD,
                AppThemeColor.FRAME_RGB,
                BorderFactory.createLineBorder(AppThemeColor.BORDER),
                "Cancel",
                15f);
        selectButton.setPreferredSize(new Dimension(128,26));
        cancelButton.setPreferredSize(new Dimension(128,26));
        selectButton.addActionListener(action -> {
            this.callback.onAction((String) this.iconsList.getSelectedValue());
            this.setVisible(false);
        });
        cancelButton.addActionListener(action -> this.setVisible(false));
        root.add(selectButton);
        root.add(cancelButton);
        return root;
    }
}