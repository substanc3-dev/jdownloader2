//jDownloader - Downloadmanager
//Copyright (C) 2013  JD-Team support@jdownloader.org
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.

package jd.plugins.hoster;

import java.io.IOException;

import jd.PluginWrapper;
import jd.nutils.encoding.Encoding;
import jd.plugins.DownloadLink;
import jd.plugins.DownloadLink.AvailableStatus;
import jd.plugins.HostPlugin;
import jd.plugins.LinkStatus;
import jd.plugins.PluginException;
import jd.plugins.PluginForHost;

@HostPlugin(revision = "$Revision$", interfaceVersion = 2, names = { "fileproject.com.br" }, urls = { "http://(www\\.)?([a-z0-9]+\\.)?fileproject\\.com\\.br/(pp/files|files/epis(odios)?)/(SD|HD|LQs?|MQ|HQ)/[^<>\"/\\s]+|http://(www\\.)?([a-z0-9]+\\.)?fileproject\\.xpg\\.uol\\.com\\.br/([a-z]{2,3}/)?files/([^/]+/){0,3}[^<>\"/\\s]+" })
public class FileProjectComBr extends PluginForHost {

    // DEV NOTES
    // DO NOT DELETE THIS PLUGIN! COUNTRY BLOCK IN EFFECT!

    public FileProjectComBr(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public String getAGBLink() {
        return "http://fileproject.com.br/";
    }

    @Override
    public AvailableStatus requestFileInformation(final DownloadLink link) throws IOException, PluginException {
        this.setBrowserExclusive();
        br.setFollowRedirects(true);
        // String[] host = new Regex(link.getDownloadURL(), "(https?://)([^:/]+)").getRow(0);
        // br.getHeaders().put("Referer", host[0] + host[1]);
        br.getPage(link.getDownloadURL());
        if (br.getHttpConnection().getResponseCode() == 403 || (br.getURL() != null && br.getURL().contains("aliancaproject.com.br/"))) {
            // country block, seems to 403 outside of Brazil!
            link.getLinkStatus().setStatusText("Provider blocks your IP Address!");
            return AvailableStatus.UNCHECKABLE;
        } else if (br.getURL() != null && !br.getURL().contains("fileproject")) {
            /* 2016-11-14: New, not sure about this, either offline or GEO-block! */
            throw new PluginException(LinkStatus.ERROR_FILE_NOT_FOUND);
        }
        String filename = br.getRegex("<div id=\"name\">([^<>\"]*?)</div>").getMatch(0);
        if (filename == null) {
            filename = br.getRegex("<title>filePROJECT - ([^<>\"]*?)</title>").getMatch(0);
        }
        if (filename == null) {
            throw new PluginException(LinkStatus.ERROR_PLUGIN_DEFECT);
        }
        link.setName(Encoding.htmlDecode(filename.trim()));
        return AvailableStatus.TRUE;
    }

    @Override
    public void handleFree(final DownloadLink downloadLink) throws Exception, PluginException {
        requestFileInformation(downloadLink);
        if (br.getHttpConnection().getResponseCode() == 403 || (br.getURL() != null && br.getURL().contains("aliancaproject.com.br/"))) {
            throw new PluginException(LinkStatus.ERROR_FATAL, "Provider blocks your IP Address!");
        }
        final String dllink = br.getRegex("\"(http://[a-z0-9\\.:]+/download/[^<>\"]*?)\"").getMatch(0);
        if (dllink == null) {
            throw new PluginException(LinkStatus.ERROR_PLUGIN_DEFECT);
        }
        dl = jd.plugins.BrowserAdapter.openDownload(br, downloadLink, dllink, true, 0);
        if (dl.getConnection().getContentType().contains("html")) {
            br.followConnection();
            if (br.containsHTML(">404 Not Found<")) {
                throw new PluginException(LinkStatus.ERROR_FILE_NOT_FOUND);
            }
            throw new PluginException(LinkStatus.ERROR_PLUGIN_DEFECT);
        }
        dl.startDownload();
    }

    @Override
    public void reset() {
    }

    @Override
    public int getMaxSimultanFreeDownloadNum() {
        return -1;
    }

    @Override
    public void resetDownloadlink(final DownloadLink link) {
    }

}