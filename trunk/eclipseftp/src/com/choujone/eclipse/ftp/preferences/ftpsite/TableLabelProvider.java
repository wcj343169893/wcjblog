package com.choujone.eclipse.ftp.preferences.ftpsite;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.choujone.eclipse.ftp.model.FtpSite;

public class TableLabelProvider extends LabelProvider implements
		ITableLabelProvider {
	public String getColumnText(Object element, int columnIndex) {

		if (element instanceof FtpSite) {
			FtpSite ftpSite = (FtpSite) element;
			if (columnIndex == 0) {
				return ftpSite.getName();
			} else if (columnIndex == 1) {
				return ftpSite.getHostIP();
			} else if (columnIndex == 2) {
				return ftpSite.getHostPort();
			} else if (columnIndex == 3) {
				return ftpSite.getLoginName();
			} else if (columnIndex == 4) {
				return ftpSite.getLoginPwd();
			}else if (columnIndex == 5) {
				return ftpSite.getLocalRoot();
			}else if (columnIndex == 6) {
				return ftpSite.getWebRoot();
			}
		}
		return "";
	}

	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}
}