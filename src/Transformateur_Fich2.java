import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.LinkedList;

public class Transformateur_Fich2 extends Transformateur_FichTxt {

    protected Transformateur_Fich2(String source, String cible, String nom_fichier) {
        super(source, cible, nom_fichier);
    }

    public Element ajouterContenuDocument(Document document) {
        final Element racine = document.createElement("FICHES");
        document.appendChild(racine);
        int cpt = 0;
        for (Fiche F : contenuFichier) {
            cpt++;
            Element fiche_element = document.createElement("FICHE");
            fiche_element.setAttribute("id", cpt + "");
            for (LinkedList<String> clefs : F.header.keySet()) {
                Element balise = document.createElement(clefs.getLast());
                balise.appendChild(document.createTextNode(formaterListe(clefs) + " " + F.header.get(clefs)));
                fiche_element.appendChild(balise);

            }
            Element langue_element_ar = document.createElement("Langue");
            langue_element_ar.setAttribute("id", "AR");

            Element langue_element_fr = document.createElement("Langue");
            langue_element_fr.setAttribute("id", "FR");

            fiche_element.appendChild(langue_element_ar);
            fiche_element.appendChild(langue_element_fr);

            for (LinkedList<String> clefs : F.ar_part.keySet()) {
                if (clefs.size() == 0)
                    continue;
                Element b = document.createElement(clefs.getLast());
                b.appendChild(document.createTextNode(formaterListe(clefs) + " " + F.ar_part.get(clefs)));
                langue_element_ar.appendChild(b);
            }

            fiche_element.appendChild(langue_element_fr);
            for (LinkedList<String> clefs : F.fr_part.keySet()) {
                if (clefs.size() == 0)
                    continue;
                Element b = document.createElement(clefs.getLast());
                b.appendChild(document.createTextNode(formaterListe(clefs) + " " + F.fr_part.get(clefs)));
                langue_element_fr.appendChild(b);
            }
            racine.appendChild(fiche_element);
        }

        return racine;
    }
}
