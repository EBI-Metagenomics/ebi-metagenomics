package uk.ac.ebi.interpro.metagenomics.memi.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Simple tool class which allows you to copy streams.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public final class StreamCopyUtil {

    private final static Log log = LogFactory.getLog(StreamCopyUtil.class);

    /**
     * Copies input stream to output stream using NIO's fast copying.
     *
     * @param is Input stream.
     * @param os Output stream.
     */
    public static void copy(final InputStream is, final OutputStream os) {
        long timeStamp = 0;
        if (log.isDebugEnabled()) {
            log.debug("Copying input stream to output stream...");
            timeStamp = System.currentTimeMillis();
        }
        // get an channel from the stream
        final ReadableByteChannel inputChannel = Channels.newChannel(is);
        final WritableByteChannel outputChannel = Channels.newChannel(os);
        // copy the channels
        try {
            ChannelTool.fastChannelCopy(inputChannel, outputChannel);
            if (log.isDebugEnabled()) {
                timeStamp = (System.currentTimeMillis() - timeStamp) / 1000;
                log.debug("Copying took " + timeStamp + " seconds.");
            }
        } catch (IOException e) {
            log.warn("Couldn't copy from input to output stream!");
        } finally {
            try {
                // closing the channels
                inputChannel.close();
                outputChannel.close();
            } catch (IOException e) {
                log.warn("Couldn't close channels!");
            }
        }
    }
}