import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class Transformateur_M674{

    public static boolean Filtrer (Node noeud){
        return (noeud.getNodeName() != "#text");
    }

        public static void Afficher(Node noeud, Document doc, Element rac) throws Exception
        {
                Element el = (Element) noeud;
                if(el.getChildNodes().getLength() >0)
                {
                        for (int cpt =0; cpt<el.getChildNodes().getLength(); cpt++)
                        {
                                Node a =el.getChildNodes().item(cpt);
                                if(Filtrer(a))
                                {
                                        Afficher(a, doc , rac);
                                }
                                else
                                {
                                        if(a.getParentNode().getNodeName() == "p" && a.getNodeValue().trim().length()>0)
                                        {
                                        Element verso_ele = (Element) doc.createElement("texte");
                                        rac.appendChild(verso_ele);
                                        verso_ele.appendChild((doc.createTextNode(a.getNodeValue().trim())));
                                        }
                                }
                        }
                }

        }
//******************************************************************************************************

static void Transformateur_M674(String source, String cible, String nom) throws Exception
        {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder parseur = factory.newDocumentBuilder();
        Document document = parseur.parse(source);
        Element liste = document.getDocumentElement();

        DOMImplementation domimp = parseur.getDOMImplementation();
        DocumentType dtd = domimp.createDocumentType("TEI_S",null,"dom.dtd");
        Document doc = domimp.createDocument(null,"TEI_S",dtd);
        doc.setXmlStandalone(true);
        Element rac = doc.getDocumentElement();

        Element M = (Element) doc.createElement("M674.xml");
        rac.appendChild(M);
        Afficher(liste,  doc, M);

        DOMSource ds = new DOMSource(doc);
        StreamResult res = new StreamResult(new File(cible+nom));
        Transformer tr = TransformerFactory.newInstance().newTransformer();tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "dom.dtd");
        tr.transform(ds, res);
        }
        }