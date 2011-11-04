package jd.gui.swing.jdgui.views.settings.panels.linkgrabberfilter.editdialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import jd.gui.swing.jdgui.views.settings.panels.linkgrabberfilter.ClickDelegater;
import jd.gui.swing.jdgui.views.settings.panels.linkgrabberfilter.editdialog.OnlineStatusFilter.OnlineStatus;
import jd.gui.swing.jdgui.views.settings.panels.linkgrabberfilter.editdialog.OnlineStatusFilter.OnlineStatusMatchtype;

import org.appwork.app.gui.MigPanel;
import org.appwork.swing.components.ExtCheckBox;
import org.appwork.swing.components.ExtTextField;
import org.appwork.swing.components.SizeSpinner;
import org.appwork.utils.StringUtils;
import org.appwork.utils.swing.SwingUtils;
import org.appwork.utils.swing.dialog.AbstractDialog;
import org.appwork.utils.swing.dialog.Dialog;
import org.jdownloader.controlling.filter.CompiledFiletypeFilter.ArchiveExtensions;
import org.jdownloader.controlling.filter.CompiledFiletypeFilter.AudioExtensions;
import org.jdownloader.controlling.filter.CompiledFiletypeFilter.ImageExtensions;
import org.jdownloader.controlling.filter.CompiledFiletypeFilter.VideoExtensions;
import org.jdownloader.controlling.filter.FilesizeFilter;
import org.jdownloader.controlling.filter.FilesizeFilter.SizeMatchType;
import org.jdownloader.controlling.filter.FiletypeFilter;
import org.jdownloader.controlling.filter.FiletypeFilter.TypeMatchType;
import org.jdownloader.controlling.filter.RegexFilter;
import org.jdownloader.controlling.filter.RegexFilter.MatchType;
import org.jdownloader.gui.translate._GUI;
import org.jdownloader.images.NewTheme;

public abstract class ConditionDialog<T> extends AbstractDialog<T> {

    protected ExtTextField txtName;

    public String getName() {
        return txtName.getText();
    }

    public void setName(String name) {
        txtName.setText(name);
    }

    public void setFilenameFilter(RegexFilter filter) {
        if (filter == null) return;
        cbFilename.setSelected(filter.isEnabled());
        cobFilename.setSelectedIndex(filter.getMatchType().ordinal());
        txtFilename.setText(filter.getRegex());
    }

    public RegexFilter getFilenameFilter() {
        return new RegexFilter(cbFilename.isSelected(), MatchType.values()[cobFilename.getSelectedIndex()], txtFilename.getText());
    }

    public void setOnlineStatusFilter(OnlineStatusFilter f) {
        if (f == null) return;
        cbOnline.setSelected(f.isEnabled());
        cobOnline.setSelectedIndex(f.getMatchType().ordinal());
        cobOnlineOptions.setSelectedIndex(f.getOnlineStatus().ordinal());

    }

    public void setFilesizeFilter(FilesizeFilter f) {
        if (f == null) return;
        cbSize.setSelected(f.isEnabled());
        cobSize.setSelectedIndex(f.getMatchType().ordinal());
        fromSize.setValue(f.getFrom());
        toSize.setValue(f.getTo());
    }

    public FilesizeFilter getFilersizeFilter() {
        return new FilesizeFilter(fromSize.getBytes(), toSize.getBytes(), cbSize.isSelected(), SizeMatchType.values()[cobSize.getSelectedIndex()]);
    }

    public void setFiletypeFilter(FiletypeFilter f) {
        if (f == null) return;
        cbType.setSelected(f.isEnabled());
        cbAudio.setSelected(f.isAudioFilesEnabled());
        cbArchive.setSelected(f.isArchivesEnabled());
        cbCustom.setSelected(!StringUtils.isEmpty(f.getCustoms()));
        txtCustumMime.setText(f.getCustoms());
        cbImage.setSelected(f.isImagesEnabled());
        cbVideo.setSelected(f.isVideoFilesEnabled());
        cobType.setSelectedIndex(f.getMatchType().ordinal());
    }

    public FiletypeFilter getFiletypeFilter() {
        return new FiletypeFilter(TypeMatchType.values()[cobType.getSelectedIndex()], cbType.isSelected(), cbAudio.isSelected(), cbVideo.isSelected(), cbArchive.isSelected(), cbImage.isSelected(), cbCustom.isSelected() ? txtCustumMime.getText() : null);
    }

    public OnlineStatusFilter getOnlineStatusFilter() {
        return new OnlineStatusFilter(OnlineStatusMatchtype.values()[cobOnline.getSelectedIndex()], cbOnline.isSelected(), OnlineStatus.values()[cobOnlineOptions.getSelectedIndex()]);
    }

    public void setSourceFilter(RegexFilter filter) {
        if (filter == null) return;
        cbSource.setSelected(filter.isEnabled());
        cobSource.setSelectedIndex(filter.getMatchType().ordinal());
        txtSource.setText(filter.getRegex());
    }

    public RegexFilter getSourceFilter() {
        return new RegexFilter(cbSource.isSelected(), MatchType.values()[cobSource.getSelectedIndex()], txtSource.getText());
    }

    public void setHosterFilter(RegexFilter filter) {
        if (filter == null) return;
        cbHoster.setSelected(filter.isEnabled());
        cobHoster.setSelectedIndex(filter.getMatchType().ordinal());
        txtHoster.setText(filter.getRegex());
    }

    public RegexFilter getHosterFilter() {
        return new RegexFilter(cbHoster.isSelected(), MatchType.values()[cobHoster.getSelectedIndex()], txtHoster.getText());
    }

    protected ExtCheckBox      cbFilename;

    protected JComboBox        cobFilename;
    protected ExtTextField     txtFilename;

    private JComponent         size;
    protected ExtCheckBox      cbSize;

    protected SizeSpinner      fromSize;
    protected SizeSpinner      toSize;
    private SpinnerNumberModel minSizeModel;
    private SpinnerNumberModel maxSizeModel;

    protected ExtCheckBox      cbType;
    protected ExtCheckBox      cbAudio;
    protected ExtCheckBox      cbVideo;
    protected ExtCheckBox      cbArchive;
    protected ExtCheckBox      cbImage;
    protected ExtTextField     txtCustumMime;
    protected ExtCheckBox      cbCustom;

    protected ExtCheckBox      cbHoster;
    protected ExtTextField     txtHoster;
    protected JComboBox        cobHoster;

    protected ExtCheckBox      cbSource;
    protected JComboBox        cobSource;
    protected ExtTextField     txtSource;

    private JComboBox          cobSize;

    private JComboBox          cobType;

    private JComboBox          cobOnline;

    private JComboBox          cobOnlineOptions;

    private ExtCheckBox        cbOnline;

    private boolean            autoset;

    public ConditionDialog() {
        super(0, _GUI._.FilterRuleDialog_FilterRuleDialog_(""), null, _GUI._.literally_save(), null);

    }

    @Override
    public JComponent layoutDialogContent() {
        panel = new MigPanel("ins 5,wrap 6", "[][][fill][][][grow,fill]", "[]");
        panel.add(createHeader(_GUI._.FilterRuleDialog_layoutDialogContent_name()), "spanx,growx,pushx");
        txtName = new ExtTextField() {

            /**
             * 
             */
            private static final long serialVersionUID = 9217479913947520012L;

            @Override
            protected void onChanged() {
                getDialog().setTitle(_GUI._.FilterRuleDialog_FilterRuleDialog_(txtName.getText()));
            }

        };
        txtName.setHelpText(_GUI._.FilterRuleDialog_layoutDialogContent_ht_name());

        panel.add(txtName, "spanx,growx,pushx,gapleft 21");

        panel.add(createHeader(getIfText()), "gaptop 10,spanx,growx,pushx");

        cobFilename = new JComboBox(new String[] { _GUI._.FilterRuleDialog_layoutDialogContent_contains(), _GUI._.FilterRuleDialog_layoutDialogContent_equals(), _GUI._.FilterRuleDialog_layoutDialogContent_contains_not(), _GUI._.FilterRuleDialog_layoutDialogContent_equals_not() });
        txtFilename = new ExtTextField();
        txtFilename.setHelpText(_GUI._.FilterRuleDialog_layoutDialogContent_ht_filename());

        JLabel lblFilename = getLabel(_GUI._.FilterRuleDialog_layoutDialogContent_lbl_filename());
        cbFilename = new ExtCheckBox(cobFilename, txtFilename) {

            @Override
            public void updateDependencies() {
                super.updateDependencies();
                updateOnline();

            }

        };
        MouseAdapter ml = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                cbFilename.setSelected(true);

            }

        };
        txtFilename.addMouseListener(ml);
        cobFilename.addMouseListener(ml);
        panel.add(cbFilename);
        panel.add(lblFilename);
        panel.add(cobFilename);
        panel.add(txtFilename, "spanx,pushx,growx");

        size = createSizeFilter();
        cobSize = new JComboBox(new String[] { _GUI._.FilterRuleDialog_layoutDialogContent_is_between(), _GUI._.FilterRuleDialog_layoutDialogContent_is_not_between() });

        JLabel lblSize = getLabel(_GUI._.FilterRuleDialog_layoutDialogContent_lbl_size());
        cbSize = new ExtCheckBox(size, cobSize) {

            @Override
            public void updateDependencies() {
                super.updateDependencies();
                updateOnline();

            }

        };
        ml = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                cbSize.setSelected(true);

            }

        };
        size.addMouseListener(ml);
        cobSize.addMouseListener(ml);
        panel.add(cbSize);
        panel.add(lblSize);
        panel.add(cobSize);
        panel.add(size, "pushx,growx,spanx");
        // Type

        ArrayList<JComponent> comp = new ArrayList<JComponent>();
        JLabel lbl, ico;
        lbl = new JLabel(AudioExtensions.AA.getDesc());
        ico = new JLabel(NewTheme.I().getIcon(AudioExtensions.AA.getIconID(), 18));
        ActionListener al = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (!cbAudio.isSelected() && !cbVideo.isSelected() && !cbArchive.isSelected() && !cbImage.isSelected() && !cbCustom.isSelected()) {
                    cbType.setSelected(false);
                }
            }
        };
        JLabel lblType = getLabel(_GUI._.FilterRuleDialog_layoutDialogContent_lbl_type());

        cobType = new JComboBox(new String[] { _GUI._.FilterRuleDialog_layoutDialogContent_is_type(), _GUI._.FilterRuleDialog_layoutDialogContent_is_not_type() });
        cbType = new ExtCheckBox() {

            @Override
            public void updateDependencies() {
                super.updateDependencies();
                updateOnline();

            }

        };
        comp.add(cobType);
        cobType.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                cbType.setSelected(true);

            }

        });
        panel.add(cbType, "aligny top");

        panel.add(lblType, "aligny top,gaptop 3");
        panel.add(cobType, "aligny top");
        cbAudio = new ExtCheckBox();
        lbl.addMouseListener(new ClickDelegater(cbAudio));
        ico.addMouseListener(new ClickDelegater(cbAudio));
        panel.add(ico, "");
        panel.add(cbAudio);
        cbAudio.addActionListener(al);
        panel.add(lbl, "spanx");
        comp.add(ico);
        comp.add(cbAudio);
        comp.add(lbl);
        // video
        lbl = new JLabel(VideoExtensions.ASF.getDesc());
        ico = new JLabel(NewTheme.I().getIcon(VideoExtensions.ASF.getIconID(), 18));
        cbVideo = new ExtCheckBox();
        cbVideo.addActionListener(al);
        lbl.addMouseListener(new ClickDelegater(cbVideo));
        ico.addMouseListener(new ClickDelegater(cbVideo));
        panel.add(ico, "skip 3");
        panel.add(cbVideo);
        panel.add(lbl, "spanx");
        comp.add(ico);
        comp.add(cbVideo);
        comp.add(lbl);
        // archives
        lbl = new JLabel(ArchiveExtensions.ACE.getDesc());
        ico = new JLabel(NewTheme.I().getIcon(ArchiveExtensions.ACE.getIconID(), 18));
        cbArchive = new ExtCheckBox();
        cbArchive.addActionListener(al);
        lbl.addMouseListener(new ClickDelegater(cbArchive));
        ico.addMouseListener(new ClickDelegater(cbArchive));
        panel.add(ico, "skip 3");
        panel.add(cbArchive);
        panel.add(lbl, "spanx");
        comp.add(ico);
        comp.add(cbArchive);
        comp.add(lbl);
        // images
        lbl = new JLabel(ImageExtensions.BMP.getDesc());
        ico = new JLabel(NewTheme.I().getIcon(ImageExtensions.BMP.getIconID(), 18));
        cbImage = new ExtCheckBox();
        cbImage.addActionListener(al);

        lbl.addMouseListener(new ClickDelegater(cbImage));
        ico.addMouseListener(new ClickDelegater(cbImage));
        panel.add(ico, "skip 3");
        panel.add(cbImage);
        panel.add(lbl, "spanx");
        comp.add(ico);
        comp.add(cbImage);
        comp.add(lbl);
        // various

        ico = new JLabel(NewTheme.I().getIcon("help", 18));
        txtCustumMime = new ExtTextField();
        txtCustumMime.setHelpText(_GUI._.FilterRuleDialog_createTypeFilter_mime_custom_help());
        txtCustumMime.addFocusListener(new FocusListener() {

            public void focusLost(FocusEvent e) {
            }

            public void focusGained(FocusEvent e) {
                cbCustom.setSelected(true);
                cbCustom.updateDependencies();
            }
        });
        cbCustom = new ExtCheckBox();
        cbCustom.addActionListener(al);
        ico.addMouseListener(new ClickDelegater(cbCustom));
        panel.add(ico, "skip 3");
        panel.add(cbCustom);
        panel.add(txtCustumMime, "spanx");
        comp.add(ico);
        comp.add(cbCustom);
        comp.add(txtCustumMime);

        ml = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                cbType.setSelected(true);

            }

        };
        for (JComponent c : comp) {
            c.addMouseListener(ml);
        }
        cbType.setDependencies(comp.toArray(new JComponent[] {}));
        // hoster
        cobHoster = new JComboBox(new String[] { _GUI._.FilterRuleDialog_layoutDialogContent_contains(), _GUI._.FilterRuleDialog_layoutDialogContent_equals(), _GUI._.FilterRuleDialog_layoutDialogContent_contains_not(), _GUI._.FilterRuleDialog_layoutDialogContent_equals_not() });
        txtHoster = new ExtTextField();
        txtHoster.setHelpText(_GUI._.FilterRuleDialog_layoutDialogContent_lbl_hoster_help());

        cbHoster = new ExtCheckBox(cobHoster, txtHoster);
        ml = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                cbHoster.setSelected(true);

            }

        };
        cobHoster.addMouseListener(ml);
        txtHoster.addMouseListener(ml);
        panel.add(cbHoster);
        panel.add(new JLabel(_GUI._.FilterRuleDialog_layoutDialogContent_lbl_hoster()));
        panel.add(cobHoster);
        panel.add(txtHoster, "spanx,pushx,growx");
        // crawler

        cobSource = new JComboBox(new String[] { _GUI._.FilterRuleDialog_layoutDialogContent_contains(), _GUI._.FilterRuleDialog_layoutDialogContent_equals(), _GUI._.FilterRuleDialog_layoutDialogContent_contains_not(), _GUI._.FilterRuleDialog_layoutDialogContent_equals_not() });
        txtSource = new ExtTextField();
        txtSource.setHelpText(_GUI._.FilterRuleDialog_layoutDialogContent_lbl_source_help());

        cbSource = new ExtCheckBox(cobSource, txtSource);
        ml = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                cbSource.setSelected(true);

            }

        };
        txtSource.addMouseListener(ml);
        cobSource.addMouseListener(ml);
        panel.add(cbSource);
        panel.add(new JLabel(_GUI._.FilterRuleDialog_layoutDialogContent_lbl_source()));
        panel.add(cobSource);
        panel.add(txtSource, "spanx,pushx,growx");

        // offline

        cobOnline = new JComboBox(new String[] { _GUI._.ConditionDialog_layoutDialogContent_online_is_(), _GUI._.ConditionDialog_layoutDialogContent_online_isnot() });
        cobOnlineOptions = new JComboBox(new String[] { _GUI._.ConditionDialog_layoutDialogContent_uncheckable_(), _GUI._.ConditionDialog_layoutDialogContent_online_(), _GUI._.ConditionDialog_layoutDialogContent_offline_() });
        cbOnline = new ExtCheckBox(cobOnline, cobOnlineOptions);

        panel.add(cbOnline);
        panel.add(new JLabel(_GUI._.FilterRuleDialog_layoutDialogContent_lbl_online()));
        panel.add(cobOnline);
        panel.add(cobOnlineOptions, "spanx,pushx,growx");
        ml = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                cbOnline.setSelected(true);

            }

        };
        cobOnline.addMouseListener(ml);
        cobOnlineOptions.addMouseListener(ml);
        return panel;
    }

    protected void updateOnline() {
        // we have to enqueue it at the edt. This is important!
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if (cbOnline == null) return;
                if (cbFilename.isSelected() || cbSize.isSelected() || cbType.isSelected()) {
                    if (!cbOnline.isSelected() || cobOnline.getSelectedIndex() != 0 || cobOnlineOptions.getSelectedIndex() != 1) {
                        autoset = true;
                        cbOnline.setSelected(true);
                        cobOnline.setSelectedIndex(0);
                        cobOnlineOptions.setSelectedIndex(1);
                        Dialog.getInstance().showMessageDialog(Dialog.STYLE_SHOW_DO_NOT_DISPLAY_AGAIN, _GUI._.literally_warning(), _GUI._.ConditionDialog_updateOnline_linkcheck_required());
                        return;
                    }
                } else if (autoset) {
                    cbOnline.setSelected(false);
                    autoset = false;

                }
            }

        });

    }

    protected String getIfText() {
        return _GUI._.FilterRuleDialog_layoutDialogContent_if();
    }

    protected MigPanel createHeader(String string) {
        MigPanel ret = new MigPanel("ins 0", "[21,fill][][grow,fill]", "[]");
        ret.add(new JSeparator());
        ret.add(SwingUtils.toBold(new JLabel(string)));
        ret.add(new JSeparator());
        return ret;
    }

    private FilterPanel createSizeFilter() {
        final JLabel to = new JLabel(NewTheme.I().getIcon("right", 14));

        minSizeModel = new SpinnerNumberModel(50000, 0l, Long.MAX_VALUE, 1) {

            @Override
            public Comparable getMaximum() {
                return (Comparable) maxSizeModel.getValue();
            }

            @Override
            public Comparable getMinimum() {
                return super.getMinimum();
            }
        };

        maxSizeModel = new SpinnerNumberModel(100 * 1024l, 0l, Long.MAX_VALUE, 1) {

            @Override
            public Comparable getMinimum() {
                return (Comparable) minSizeModel.getValue();
            }

        };
        fromSize = new SizeSpinner(minSizeModel);

        toSize = new SizeSpinner(maxSizeModel);

        toSize.setValue(10 * 1024 * 1024l * 1024l);
        final FilterPanel ret = new FilterPanel("[grow,fill][][grow,fill]", "[]");

        ret.add(fromSize, "sg 1");
        ret.add(to);
        ret.add(toSize, "sg 1");

        return ret;
    }

    private JLabel getLabel(String filterRuleDialog_layoutDialogContent_lbl_name) {
        JLabel lbl = new JLabel(filterRuleDialog_layoutDialogContent_lbl_name);
        // lbl.setEnabled(false);
        return lbl;
    }

}
