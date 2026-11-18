package com.nurcodex.nurgram;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exteragram.messenger.preferences.BasePreferencesActivity;
import com.exteragram.messenger.preferences.components.HeaderSettingsCell;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.R;
import org.telegram.messenger.browser.Browser;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.TextCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Components.BulletinFactory;

public class AboutNurGramActivity extends BasePreferencesActivity {

    private int headerRow;
    private int headerDividerRow;

    private int devHeaderRow;
    private int appNameRow;
    private int developerRow;
    private int channelRow;
    private int usernameRow;
    private int creditRow;
    private int devDividerRow;

    private int creditsHeaderRow;
    private int creditsInfoRow;

    @Override
    protected void updateRowsId() {
        super.updateRowsId();

        headerRow = newRow();
        headerDividerRow = newRow();

        devHeaderRow = newRow();
        appNameRow = newRow();
        developerRow = newRow();
        channelRow = newRow();
        usernameRow = newRow();
        creditRow = newRow();
        devDividerRow = newRow();

        creditsHeaderRow = newRow();
        creditsInfoRow = newRow();
    }

    @Override
    protected void onItemClick(View view, int position, float x, float y) {
        if (position == channelRow) {
            openTelegramUsername("NurCodex");
        } else if (position == usernameRow || position == developerRow) {
            openTelegramUsername("NurCodexx");
        } else if (position == creditRow) {
            AndroidUtilities.addToClipboard(LocaleController.getString("NurGramDeveloperCredit", R.string.NurGramDeveloperCredit));
            BulletinFactory.of(this).createCopyBulletin(LocaleController.getString("TextCopied", R.string.TextCopied)).show();
        }
    }

    private void openTelegramUsername(String username) {
        try {
            MessagesController.getInstance(currentAccount).openByUserName(username, this, 1);
        } catch (Exception e) {
            Browser.openUrl(getParentActivity(), "https://t.me/" + username);
        }
    }

    @Override
    protected String getTitle() {
        return LocaleController.getString("AboutNurGram", R.string.AboutNurGram);
    }

    @Override
    protected BaseListAdapter createAdapter(Context context) {
        return new ListAdapter(context);
    }

    private class ListAdapter extends BaseListAdapter {

        public ListAdapter(Context context) {
            super(context);
        }

        @Override
        public int getItemCount() {
            return rowCount;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case 1:
                    holder.itemView.setBackground(Theme.getThemedDrawable(mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                    break;
                case 2:
                    TextCell textCell = (TextCell) holder.itemView;
                    if (position == appNameRow) {
                        textCell.setTextAndValueAndIcon(
                                LocaleController.getString("NurGramAppNameLabel", R.string.NurGramAppNameLabel),
                                LocaleController.getString("NurGramAppNameValue", R.string.NurGramAppNameValue),
                                R.drawable.msg_info,
                                true
                        );
                    } else if (position == developerRow) {
                        textCell.setTextAndValueAndIcon(
                                LocaleController.getString("NurGramDeveloperLabel", R.string.NurGramDeveloperLabel),
                                LocaleController.getString("NurGramDeveloperName", R.string.NurGramDeveloperName),
                                R.drawable.msg_contacts,
                                true
                        );
                    } else if (position == channelRow) {
                        textCell.setTextAndValueAndIcon(
                                LocaleController.getString("NurGramChannelLabel", R.string.NurGramChannelLabel),
                                LocaleController.getString("NurGramChannelHandle", R.string.NurGramChannelHandle),
                                R.drawable.msg_channel,
                                true
                        );
                    } else if (position == usernameRow) {
                        textCell.setTextAndValueAndIcon(
                                LocaleController.getString("NurGramUsernameLabel", R.string.NurGramUsernameLabel),
                                LocaleController.getString("NurGramUsernameHandle", R.string.NurGramUsernameHandle),
                                R.drawable.msg_contacts,
                                true
                        );
                    } else if (position == creditRow) {
                        textCell.setTextAndValue(
                                LocaleController.getString("NurGramCreditLabel", R.string.NurGramCreditLabel),
                                LocaleController.getString("NurGramDeveloperCredit", R.string.NurGramDeveloperCredit),
                                false
                        );
                    }
                    break;
                case 3:
                    HeaderCell headerCell = (HeaderCell) holder.itemView;
                    if (position == devHeaderRow) {
                        headerCell.setText(LocaleController.getString("NurGramDeveloperInfoHeader", R.string.NurGramDeveloperInfoHeader));
                    } else if (position == creditsHeaderRow) {
                        headerCell.setText(LocaleController.getString("NurGramCreditsHeader", R.string.NurGramCreditsHeader));
                    }
                    break;
                case 4:
                    HeaderSettingsCell headerSettingsCell = (HeaderSettingsCell) holder.itemView;
                    headerSettingsCell.setPadding(0, 0, 0, 0);
                    break;
                case 8:
                    TextInfoPrivacyCell infoCell = (TextInfoPrivacyCell) holder.itemView;
                    if (position == creditsInfoRow) {
                        infoCell.setText(LocaleController.getString("NurGramCreditsText", R.string.NurGramCreditsText));
                        infoCell.setBackground(Theme.getThemedDrawable(mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    }
                    break;
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == headerDividerRow || position == devDividerRow) {
                return 1;
            } else if (position == devHeaderRow || position == creditsHeaderRow) {
                return 3;
            } else if (position == headerRow) {
                return 4;
            } else if (position == creditsInfoRow) {
                return 8;
            }
            return 2;
        }
    }
}
