import java.io.InputStream;
import java.net.URL;

import org.xml.sax.InputSource;

import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.sax.BoilerpipeSAXInput;

class ExtractDataFromArticle
{
	public static void main(final String[] args) throws Exception 
	{
		URL url;
        url = new URL(
                "http://www.thehindu.com/features/metroplus/welcome-the-lunar-new-year/article6909002.ece");

        // NOTE We ignore HTTP-based character encoding in this demo...
        final InputStream urlStream = url.openStream();
        final InputSource is = new InputSource(urlStream);

        final BoilerpipeSAXInput in = new BoilerpipeSAXInput(is);
        final TextDocument doc = in.getTextDocument();
        urlStream.close();

        // You have the choice between different Extractors

        // System.out.println(DefaultExtractor.INSTANCE.getText(doc));
        System.out.println(ArticleExtractor.INSTANCE.getText(doc));
	}
}