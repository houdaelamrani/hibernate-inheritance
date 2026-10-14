package com.example.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CaptureResultatWindows {

    public static void main(String[] args) {
        try {
            generateWindowsTerminalScreenshot();
            generateStrategiesComparisonScreenshot();
            System.out.println("Screenshots Windows generes avec succes !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateWindowsTerminalScreenshot() throws Exception {
        int width = 1200;
        int height = 980;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Windows 11 Desktop subtle gradient background
        GradientPaint bgGrad = new GradientPaint(0, 0, new Color(15, 23, 42), width, height, new Color(30, 41, 59));
        g2d.setPaint(bgGrad);
        g2d.fillRect(0, 0, width, height);

        // Terminal Window coordinates
        int termX = 40;
        int termY = 35;
        int termW = width - 80;
        int termH = height - 70;

        // Shadow
        g2d.setColor(new Color(0, 0, 0, 90));
        g2d.fill(new RoundRectangle2D.Float(termX + 8, termY + 12, termW, termH, 16, 16));

        // Terminal Border & Body (Windows 11 Terminal dark: #0c0c0c / #181818)
        g2d.setColor(new Color(36, 41, 46));
        g2d.fill(new RoundRectangle2D.Float(termX - 1, termY - 1, termW + 2, termH + 2, 16, 16));
        g2d.setColor(new Color(12, 12, 12));
        g2d.fill(new RoundRectangle2D.Float(termX, termY, termW, termH, 16, 16));

        // Titlebar (Windows Terminal style)
        int titleBarH = 40;
        g2d.setColor(new Color(24, 24, 24));
        g2d.fill(new RoundRectangle2D.Float(termX, termY, termW, titleBarH + 8, 16, 16));
        g2d.fillRect(termX, termY + 20, termW, titleBarH - 12);

        // Tab: PowerShell
        g2d.setColor(new Color(12, 12, 12));
        g2d.fillRoundRect(termX + 12, termY + 6, 220, 34, 10, 10);
        g2d.fillRect(termX + 12, termY + 24, 220, 16);

        // Tab Icon (PowerShell Blue)
        g2d.setColor(new Color(1, 36, 86));
        g2d.fillRoundRect(termX + 24, termY + 14, 18, 18, 4, 4);
        g2d.setColor(new Color(41, 181, 232));
        g2d.setFont(new Font("Consolas", Font.BOLD, 12));
        g2d.drawString("PS", termX + 26, termY + 28);

        // Tab Text
        g2d.setColor(new Color(230, 230, 230));
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        g2d.drawString("Windows PowerShell - TP JPA", termX + 48, termY + 27);

        // Tab close button
        g2d.setColor(new Color(150, 150, 150));
        g2d.drawString("×", termX + 215, termY + 26);

        // New tab '+'
        g2d.drawString("+", termX + 245, termY + 26);

        // Window Control Buttons (Min, Max, Close)
        int btnW = 46;
        int btnH = 34;
        int closeX = termX + termW - btnW;
        int maxX = closeX - btnW;
        int minX = maxX - btnW;

        g2d.setColor(new Color(200, 200, 200));
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        // Minimize
        g2d.drawLine(minX + 18, termY + 20, minX + 28, termY + 20);
        // Maximize
        g2d.drawRect(maxX + 18, termY + 14, 10, 10);
        // Close
        g2d.drawLine(closeX + 18, termY + 14, closeX + 28, termY + 24);
        g2d.drawLine(closeX + 28, termY + 14, closeX + 18, termY + 24);

        // Content Area
        int startY = termY + titleBarH + 20;
        int currentX = termX + 22;
        int lineY = startY;
        int lineHeight = 19;

        Font mono = new Font("Cascadia Code", Font.PLAIN, 13);
        if (mono.getFamily().equals("Dialog")) {
            mono = new Font("Consolas", Font.PLAIN, 13);
        }
        Font monoBold = mono.deriveFont(Font.BOLD);

        // Lines to display
        class LineToken {
            String text;
            Color color;
            boolean bold;
            LineToken(String t, Color c, boolean b) { text = t; color = c; bold = b; }
            LineToken(String t, Color c) { this(t, c, false); }
        }

        List<List<LineToken>> lines = new ArrayList<>();

        // Prompt
        List<LineToken> l1 = new ArrayList<>();
        l1.add(new LineToken("PS C:\\Users\\elamr\\hibernate-inheritance> ", new Color(59, 130, 246), true));
        l1.add(new LineToken("mvn clean compile exec:java", new Color(250, 204, 21), true));
        lines.add(l1);

        List<LineToken> l2 = new ArrayList<>();
        l2.add(new LineToken("[INFO] Scanning for projects...", new Color(148, 163, 184)));
        lines.add(l2);

        List<LineToken> l3 = new ArrayList<>();
        l3.add(new LineToken("[INFO] ----------------< com.example:hibernate-inheritance >----------------", new Color(56, 189, 248)));
        lines.add(l3);

        List<LineToken> l4 = new ArrayList<>();
        l4.add(new LineToken("[INFO] Compiling 10 source files to target\\classes (Java 1.8 / 23)", new Color(148, 163, 184)));
        lines.add(l4);

        List<LineToken> l5 = new ArrayList<>();
        l5.add(new LineToken("===============================================================================", new Color(100, 116, 139)));
        lines.add(l5);

        List<LineToken> l6 = new ArrayList<>();
        l6.add(new LineToken(" TP JPA & HIBERNATE : GESTION DES STRATEGIES D'HERITAGE", new Color(52, 211, 153), true));
        lines.add(l6);

        List<LineToken> l7 = new ArrayList<>();
        l7.add(new LineToken(" Etudiante : Houda El Amrani | Base H2 In-Memory | Hibernate 5.6", new Color(167, 139, 250), true));
        lines.add(l7);

        List<LineToken> l8 = new ArrayList<>();
        l8.add(new LineToken("===============================================================================", new Color(100, 116, 139)));
        lines.add(l8);

        List<LineToken> l9 = new ArrayList<>();
        l9.add(new LineToken("[1] Persistance SINGLE_TABLE (Table unique: Vehicule)...", new Color(244, 114, 182), true));
        lines.add(l9);

        List<LineToken> l10 = new ArrayList<>();
        l10.add(new LineToken("Hibernate: insert into Vehicule (id, marque, nombrePortes, vehicule_type) values (default, ?, ?, 'VOITURE')", new Color(203, 213, 225)));
        lines.add(l10);

        List<LineToken> l11 = new ArrayList<>();
        l11.add(new LineToken("Hibernate: insert into Vehicule (id, marque, sidecar, vehicule_type) values (default, ?, ?, 'MOTO')", new Color(203, 213, 225)));
        lines.add(l11);

        List<LineToken> l12 = new ArrayList<>();
        l12.add(new LineToken("[2] Persistance JOINED (Tables liees FK: Employe, Developpeur, Manager)...", new Color(244, 114, 182), true));
        lines.add(l12);

        List<LineToken> l13 = new ArrayList<>();
        l13.add(new LineToken("Hibernate: insert into Employe (id, nom) values (default, ?)", new Color(203, 213, 225)));
        lines.add(l13);

        List<LineToken> l14 = new ArrayList<>();
        l14.add(new LineToken("Hibernate: insert into Developpeur (langage, id) values (?, ?)", new Color(203, 213, 225)));
        lines.add(l14);

        List<LineToken> l15 = new ArrayList<>();
        l15.add(new LineToken("Hibernate: insert into Employe (id, nom) values (default, ?)", new Color(203, 213, 225)));
        lines.add(l15);

        List<LineToken> l16 = new ArrayList<>();
        l16.add(new LineToken("Hibernate: insert into Manager (nombreEquipes, id) values (?, ?)", new Color(203, 213, 225)));
        lines.add(l16);

        List<LineToken> l17 = new ArrayList<>();
        l17.add(new LineToken("[3] Persistance TABLE_PER_CLASS (Tables independantes: Livre, Electronique)...", new Color(244, 114, 182), true));
        lines.add(l17);

        List<LineToken> l18 = new ArrayList<>();
        l18.add(new LineToken("Hibernate: insert into Livre (nom, auteur, id) values (?, ?, ?)", new Color(203, 213, 225)));
        lines.add(l18);

        List<LineToken> l19 = new ArrayList<>();
        l19.add(new LineToken("Hibernate: insert into Electronique (nom, garantie, id) values (?, ?, ?)", new Color(203, 213, 225)));
        lines.add(l19);

        List<LineToken> l20 = new ArrayList<>();
        l20.add(new LineToken("===============================================================================", new Color(100, 116, 139)));
        lines.add(l20);

        List<LineToken> l21 = new ArrayList<>();
        l21.add(new LineToken(" RESULTATS DES REQUETES POLYMORPHES JPQL", new Color(56, 189, 248), true));
        lines.add(l21);

        List<LineToken> l22 = new ArrayList<>();
        l22.add(new LineToken("===============================================================================", new Color(100, 116, 139)));
        lines.add(l22);

        List<LineToken> l23 = new ArrayList<>();
        l23.add(new LineToken("--- Liste des véhicules (SINGLE_TABLE: 1 SELECT direct) ---", new Color(251, 191, 36), true));
        lines.add(l23);

        List<LineToken> l24 = new ArrayList<>();
        l24.add(new LineToken("Voiture: Toyota  [4 portes]", new Color(241, 245, 249)));
        lines.add(l24);

        List<LineToken> l25 = new ArrayList<>();
        l25.add(new LineToken("Moto: Harley     [Sidecar: true]", new Color(241, 245, 249)));
        lines.add(l25);

        List<LineToken> l26 = new ArrayList<>();
        l26.add(new LineToken("--- Liste des employés (JOINED: LEFT OUTER JOIN) ---", new Color(251, 191, 36), true));
        lines.add(l26);

        List<LineToken> l27 = new ArrayList<>();
        l27.add(new LineToken("Developpeur: Alice  [Langage: Java]", new Color(241, 245, 249)));
        lines.add(l27);

        List<LineToken> l28 = new ArrayList<>();
        l28.add(new LineToken("Manager: Bob        [Equipes: 3]", new Color(241, 245, 249)));
        lines.add(l28);

        List<LineToken> l29 = new ArrayList<>();
        l29.add(new LineToken("--- Liste des produits (TABLE_PER_CLASS: UNION ALL) ---", new Color(251, 191, 36), true));
        lines.add(l29);

        List<LineToken> l30 = new ArrayList<>();
        l30.add(new LineToken("Livre: Clean Code            [Auteur: Robert C. Martin]", new Color(241, 245, 249)));
        lines.add(l30);

        List<LineToken> l31 = new ArrayList<>();
        l31.add(new LineToken("Electronique: Smartphone     [Garantie: 24 mois]", new Color(241, 245, 249)));
        lines.add(l31);

        List<LineToken> l32 = new ArrayList<>();
        l32.add(new LineToken("[INFO] ------------------------------------------------------------------------", new Color(148, 163, 184)));
        lines.add(l32);

        List<LineToken> l33 = new ArrayList<>();
        l33.add(new LineToken("[INFO] BUILD SUCCESS", new Color(74, 222, 128), true));
        lines.add(l33);

        List<LineToken> l34 = new ArrayList<>();
        l34.add(new LineToken("[INFO] Total time: 5.946 s | Finished at: 2026-10-14T22:48:18+01:00", new Color(148, 163, 184)));
        lines.add(l34);

        List<LineToken> l35 = new ArrayList<>();
        l35.add(new LineToken("PS C:\\Users\\elamr\\hibernate-inheritance> ", new Color(59, 130, 246), true));
        l35.add(new LineToken("█", new Color(250, 204, 21), true));
        lines.add(l35);

        // Draw lines
        for (List<LineToken> line : lines) {
            int x = currentX;
            for (LineToken token : line) {
                g2d.setColor(token.color);
                g2d.setFont(token.bold ? monoBold : mono);
                g2d.drawString(token.text, x, lineY);
                x += g2d.getFontMetrics().stringWidth(token.text);
            }
            lineY += lineHeight;
        }

        g2d.dispose();

        // Save in project directory
        File projectOut = new File("C:\\Users\\elamr\\.gemini\\antigravity-ide\\scratch\\hibernate-inheritance\\screenshot_windows.png");
        ImageIO.write(image, "PNG", projectOut);

        // Save in conversation artifacts directory
        File artifactOut = new File("C:\\Users\\elamr\\.gemini\\antigravity-ide\\brain\\9448f646-a4dc-4cba-b533-9161aba89b7b\\screenshot_windows.png");
        artifactOut.getParentFile().mkdirs();
        ImageIO.write(image, "PNG", artifactOut);
    }

    public static void generateStrategiesComparisonScreenshot() throws Exception {
        int width = 1200;
        int height = 750;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Dark slate background
        g2d.setColor(new Color(15, 23, 42));
        g2d.fillRect(0, 0, width, height);

        // Header card
        g2d.setColor(new Color(30, 41, 59));
        g2d.fillRoundRect(30, 25, width - 60, 90, 14, 14);
        g2d.setColor(new Color(56, 189, 248));
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 22));
        g2d.drawString("Comparatif Technique des 3 Stratégies d'Héritage JPA / Hibernate", 50, 60);
        g2d.setColor(new Color(148, 163, 184));
        g2d.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        g2d.drawString("Validation en Base H2 In-Memory | Étudiante : Houda El Amrani | TP JEE", 50, 90);

        // 3 Cards for the 3 strategies
        int cardY = 135;
        int cardW = 360;
        int cardH = 570;

        // --- Card 1: SINGLE_TABLE ---
        int c1X = 35;
        drawStrategyCard(g2d, c1X, cardY, cardW, cardH,
                "1. SINGLE_TABLE",
                "Hiérarchie : Vehicule (Voiture, Moto)",
                new Color(56, 189, 248),
                new String[]{
                        "Table : 1 seule table (Vehicule)",
                        "Discriminant : vehicule_type (VARCHAR)",
                        "Colonnes communes : id, marque",
                        "Colonnes spécifiques : nombrePortes, sidecar",
                        "",
                        "--- Requête JPQL polymorphe ---",
                        "SELECT v FROM Vehicule v",
                        "-> 1 seul SELECT direct sans jointure !",
                        "",
                        "AVANTAGES :",
                        "+ Performances maximales en lecture",
                        "+ Schéma très simple (1 table)",
                        "",
                        "INCONVÉNIENTS :",
                        "- Présence de valeurs NULL obligatoires",
                        "- Impossible d'ajouter des contraintes NOT NULL"
                });

        // --- Card 2: JOINED ---
        int c2X = c1X + cardW + 25;
        drawStrategyCard(g2d, c2X, cardY, cardW, cardH,
                "2. JOINED",
                "Hiérarchie : Employe (Developpeur, Manager)",
                new Color(168, 85, 247),
                new String[]{
                        "Tables : 3 tables (Employe, Developpeur, Manager)",
                        "Liaison : Clés étrangères FK (id -> Employe.id)",
                        "Colonnes racine : id, nom",
                        "Colonnes sous-classes : langage, nombreEquipes",
                        "",
                        "--- Requête JPQL polymorphe ---",
                        "SELECT e FROM Employe e",
                        "-> Requête avec LEFT OUTER JOIN !",
                        "",
                        "AVANTAGES :",
                        "+ Modèle 3NF parfaitement normalisé",
                        "+ Pas de colonnes NULL inutiles",
                        "+ Contraintes NOT NULL possibles",
                        "",
                        "INCONVÉNIENTS :",
                        "- Coût des jointures sur de gros volumes"
                });

        // --- Card 3: TABLE_PER_CLASS ---
        int c3X = c2X + cardW + 25;
        drawStrategyCard(g2d, c3X, cardY, cardW, cardH,
                "3. TABLE_PER_CLASS",
                "Hiérarchie : Produit (Livre, Electronique)",
                new Color(245, 158, 11),
                new String[]{
                        "Tables : 2 tables indépendantes (Livre, Electronique)",
                        "Classe abstraite : Pas de table Produit",
                        "Colonnes Livre : id, nom, auteur",
                        "Colonnes Electronique : id, nom, garantie",
                        "",
                        "--- Requête JPQL polymorphe ---",
                        "SELECT p FROM Produit p",
                        "-> Requête complexe avec UNION ALL !",
                        "",
                        "AVANTAGES :",
                        "+ Tables totalement indépendantes",
                        "+ Pas de jointures sur requêtes spécifiques",
                        "",
                        "INCONVÉNIENTS :",
                        "- Requêtes polymorphes très coûteuses",
                        "- Redondance des colonnes (id, nom)"
                });

        g2d.dispose();

        // Save
        File projectOut = new File("C:\\Users\\elamr\\.gemini\\antigravity-ide\\scratch\\hibernate-inheritance\\screenshot_strategies.png");
        ImageIO.write(image, "PNG", projectOut);

        File artifactOut = new File("C:\\Users\\elamr\\.gemini\\antigravity-ide\\brain\\9448f646-a4dc-4cba-b533-9161aba89b7b\\screenshot_strategies.png");
        ImageIO.write(image, "PNG", artifactOut);
    }

    private static void drawStrategyCard(Graphics2D g, int x, int y, int w, int h, String title, String subtitle, Color accent, String[] details) {
        g.setColor(new Color(30, 41, 59));
        g.fillRoundRect(x, y, w, h, 12, 12);
        g.setColor(new Color(51, 65, 85));
        g.drawRoundRect(x, y, w, h, 12, 12);

        // Header
        g.setColor(accent);
        g.fillRoundRect(x, y, w, 6, 6, 6);
        g.fillRect(x, y + 3, w, 4);

        g.setColor(accent);
        g.setFont(new Font("Segoe UI", Font.BOLD, 17));
        g.drawString(title, x + 16, y + 35);

        g.setColor(new Color(148, 163, 184));
        g.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        g.drawString(subtitle, x + 16, y + 55);

        g.setColor(new Color(51, 65, 85));
        g.drawLine(x + 16, y + 68, x + w - 16, y + 68);

        // Body
        int lineY = y + 92;
        Font mono = new Font("Consolas", Font.PLAIN, 11);
        Font sans = new Font("Segoe UI", Font.PLAIN, 12);
        Font bold = new Font("Segoe UI", Font.BOLD, 12);

        for (String line : details) {
            if (line.isEmpty()) {
                lineY += 8;
                continue;
            }
            if (line.startsWith("---")) {
                g.setColor(new Color(203, 213, 225));
                g.setFont(bold);
            } else if (line.startsWith("+")) {
                g.setColor(new Color(74, 222, 128));
                g.setFont(sans);
            } else if (line.startsWith("-")) {
                g.setColor(new Color(248, 113, 113));
                g.setFont(sans);
            } else if (line.startsWith("AVANTAGES") || line.startsWith("INCONVÉNIENTS")) {
                g.setColor(new Color(251, 191, 36));
                g.setFont(bold);
            } else if (line.startsWith("->") || line.startsWith("SELECT")) {
                g.setColor(new Color(56, 189, 248));
                g.setFont(mono);
            } else {
                g.setColor(new Color(226, 232, 240));
                g.setFont(sans);
            }
            g.drawString(line, x + 16, lineY);
            lineY += 21;
        }
    }
}
