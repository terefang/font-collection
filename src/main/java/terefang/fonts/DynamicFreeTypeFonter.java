package terefang.fonts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;

public class DynamicFreeTypeFonter
{
    public static final int normalizedSize(int _fs, boolean _noreExact)
    {
        for(int _i = generalizedSizes.length-1; _i>=0; _i--)
        {
            int _s = generalizedSizes[_i];
            if(_s>_fs) continue;

            if(_noreExact) return _s;

            int _n = nextPowerOf2(_s);
            int _l = lastPowerOf2(_s);
            int _m = _n - (_l>>1);
            int _r = (_s>=_m) ? _m : _l;
            //System.err.println(_s+" = "+_n+" "+_m+" "+_l+" -> "+_r);
            return _r;
        }
        return _fs;
    }

    public static final int nextPowerOf2(int a)
    {
        int b = 1;
        while(b < a)
        {
            b <<= 1;
        }
        return b;
    }

    public static final int lastPowerOf2(int a)
    {
        int b = 1;
        while(b < a)
        {
            b <<= 1;
        }
        return b>>1;
    }

    public static final int[] generalizedSizes = {
                //  ! convert dots to pixels 96PPI / 72DPI
                //  1	≈ 0.353 mm	American[8]			Achtelpetit	Achtste petit
                //  1+1/2 ≈ 0.529 mm	German			Achtelcicero	Achtste cicero
                //  2	≈ 0.706 mm	Saxon			Non Plus Ultra[9]     Viertelpetit	Non plus ultra[10]     Vierde petit
                //  2+1/2≈ 0.882 mm	Norse		Microscopique[11]	Microscopique[9]	Microscoop Microscopie
        4,      //  3	≈ 1.058 mm	Excelsior[12][14]	Minikin[12]	Diamant	Brillant[9] Viertelcicero	Kwart cicero
        5,       //  3+1/2≈ 1.235 mm	Ruby Brilliant[15]
        6,      //  4	≈ 1.411 mm	Brilliant	Perle	Diamant Halbpetit[9]	Robijn Diamant Halve petit
                //  4+1/4 ≈ 1.499 mm		Gem
                //  4+1/2 ≈ 1.588 mm	Diamond
        7,      //  5	≈ 1.764 mm	Pearl	Parisienne     Sédanoise	Perl	Parel     Parisienne	八	Bā	"Eight"
                //  5+1/2 ≈ 1.940 mm	Agate	Ruby[16][17]		七	Qī	"Seven"
        8,      //  6	≈ 2.117 mm	Nonpareil	Nonpareille	Nonpareille	Nonparel  Nonpareil
                //  6+1/2 ≈ 2.293 mm	Minionette[18]	Emerald[18]		Insertio	Insertio	小六	Xiǎoliù	"Little Six"
        10,     //  7	≈ 2.469 mm	Minion	Mignonne	Kolonel	Kolonel          Mignon
                //  7+1/2 ≈ 2.646 mm		Petit-texte		六	Liù	"Six"
        11,     //  8	≈ 2.822 mm	Brevier	Gaillarde    Petit-texte[15]	Petit    Jungfer[15]	Petit    Brevier[15]
        12,     //  9	≈ 3.175 mm	Bourgeois[20]	Petit-romain    Gaillarde[19]	Bourgeois    Borgis[21]	Borgis    Burgeois[19]	小五	Xiǎowǔ	"Little Five"
        13,     //  10	≈ 3.528 mm	Long Primer	Philosophie	Korpus    Garmond[21]	Corpus        Garamond
                //  10+1/2≈ 3.704 mm			五	Wǔ	"Five"
        15,     //  11	≈ 3.881 mm	Small Pica	Cicéro	Rheinländer     Discendian[21]	Mediaan         Rheinländer
        16,     //  12	≈ 4.233 mm	Pica	St.-Augustin	Cicero	Cicero    Augustijn	小四	Xiǎosì	"Little Four"
        19,     //  14	≈ 4.939 mm	English	Gros-texte[22]	Mittel	Grote cicero     Grote augustijn    Mediaan[23]	四	Sì	"Four"
        20,     //  15	≈ 5.292 mm		Gros-texte[22]		小三	Xiǎosān	"Little Three"
        22,     //  16	≈ 5.644 mm	Columbian Exchange		Gros-texte[22]	Tertia	Tertia	三	Sān	"Three
        24,     //  18	≈ 6.350 mm	Great Primer	Gros-romain    1+1/2 Cicero	Paragon     Tekst[24]	小二	Xiǎoèr	"Little Two"
        27,     //  20	≈ 7.056 mm	Paragon[2][4]	Petit-parangon	Text    Secunda[9]
        29,     //  22	≈ 7.761 mm	Double Small Pica[2][4]	Gros-parangon		二	Èr	"Two"
        32,     //  24	≈ 8.467 mm	Double Pica	Palestine	Doppelcicero	Dubbele cicero    Palestine	小一	Xiǎoyī	"Little One"
        35,     //  26	≈ 9.172 mm			一	Yī	"One"
        37,     //  28	≈ 9.878 mm	Double English	Petit-canon	Doppelmittel	Dubbele mediaan
        40,     //  30	≈ 10.583 mm	Five-line Nonpareil
        43,     //  32	≈ 11.289 mm	Double Columbian			Kleine Kanon    Doppeltertia[25]	Dubbele tertia
        48,     //  36	≈ 12.7 mm	Double Great Primer	Trismégiste	Kanon    Canon[9]	Kanon	小初	Xiǎochū	"Little Initial"
        54,     //  40	≈ 14.111 mm	Double Paragon			Doppeltext[26]    Große Kanon[27]
        56,     //  42	≈ 14.817 mm	Seven-line Nonpareil			Große Kanon[27]	Grote Kanon	初	Chū	"Initial"
        59,     //  44	≈ 15.522 mm	Canon		Gros-canon[28]	Missal[29]	Parijs Romein[30
        64,     //  48	≈ 16.933 mm	Four-line Pica    French canon	Canon	Gros-canon[28]	Kleine Missal	Konkordanz    Kleine missaal
        72,     //  54	≈ 19.050 mm			Missal	Missaal
        75,     //  56	≈ 19.756 mm		Double-canon
        80,     //  60	≈ 21.167 mm	Five-line pica			Große Missal	Sabon
        88,     //  66	≈ 23.283 mm			Große Sabon[9]	Grote sabon
        96,     //  72	≈ 25.4 mm	Six-line pica    Inch		Double-trismégiste	Sabon    Sechscicero[9]    Kleine Sabon[26]	6 cicero
        112,    //  84	≈ 29.633 mm	Seven-line pica			Siebencicero[9]    Große Sabon[26]	7 cicero
        118,    //  88	≈ 31.044 mm		Triple-canon
        128,    //  96	≈ 33.867 mm	Eight-line pica		Grosse-nonpareille	Achtcicero[9]    Real[31]	8 cicero
        133,    //  100	≈ 35.278 mm		Moyenne de fonte
        144,    //  108	≈ 38.1 mm	Nine-line pica			Imperial[26]	9 cicero
        162,
        192,
        216,
        248,
        256
    };

    public static final int CODERANGE_DEFAULT = 0;
    public static final int CODERANGE_LATIN = 1;
    public static final int CODERANGE_GREEK = 2;
    public static final int CODERANGE_CYRILLIC = 3;
    public static final int CODERANGE_CJK = 4;
    public static final int CODERANGE_ARROWS = 5;
    public static final int CODERANGE_DINGBATS = 6;

    public static final int[][] standardCodeRanges = {
            { 0x0020 , 0x007e , CODERANGE_DEFAULT}, // Basic Latin, ASCII
            { 0x00a1 , 0x00ff , CODERANGE_DEFAULT}, // Latin-1 Supplement
            { 0x0100 , 0x01ff , CODERANGE_LATIN}, // Latin Extended-A
            { 0x0200 , 0x024f , CODERANGE_LATIN}, // Latin Extended-B
            { 0x02C6 , 0x02C7 , CODERANGE_DEFAULT}, // Circumflex, Caron
            { 0x02D8 , 0x02DD , CODERANGE_DEFAULT}, // Breve, Dot Above, Ring Above, Ogonek, Small Tilde, Double Acute Accent
            { 0x0370 , 0x03ff , CODERANGE_GREEK}, // Greek, Coptic
            { 0x0400 , 0x04ff , CODERANGE_CYRILLIC}, // cyrillic
            { 0x0500 , 0x052f , CODERANGE_CYRILLIC}, // cyrillic supplement
            { 0x1e00 , 0x1eff , CODERANGE_LATIN}, // Latin Extended Additional
            { 0x2010 , 0x205f , CODERANGE_DEFAULT}, // General Punctuation
            { 0x20a0 , 0x20bf , CODERANGE_DEFAULT}, // Currency Symbols -- Pound Sign, Euro Sign, BitCoin Sign
            { 0x2122 , 0x2122 , CODERANGE_DEFAULT}, // (TM)
            // 0 BMP	U+2000..U+206F	General Punctuation	112	111	Common (109 characters), Inherited (2 characters)
            // 0 BMP	U+2070..U+209F	Superscripts and Subscripts	48	42	Latin (15 characters), Common (27 characters)
            // 0 BMP	U+20A0..U+20CF	Currency Symbols	48	32	Common
            // 0 BMP	U+20D0..U+20FF	Combining Diacritical Marks for Symbols	48	33	Inherited
            // 0 BMP	U+2100..U+214F	Letterlike Symbols	80	80	Greek (1 character), Latin (4 characters), Common (75 characters)
            // 0 BMP	U+2150..U+218F	Number Forms	64	60	Latin (41 characters), Common (19 characters)
            { 0x2190 , 0x21FF , CODERANGE_ARROWS}, // Arrows
            // 0 BMP	U+2200..U+22FF	Mathematical Operators	256	256	Common
            { 0x2212 , 0x2212 , CODERANGE_DEFAULT}, // minus
            // 0 BMP	U+2300..U+23FF	Miscellaneous Technical	256	256	Common
            // 0 BMP	U+2400..U+243F	Control Pictures	64	39	Common
            // 0 BMP	U+2440..U+245F	Optical Character Recognition	32	11	Common
            // 0 BMP	U+2460..U+24FF	Enclosed Alphanumerics	160	160	Common
            // 0 BMP	U+2500..U+257F	Box Drawing	128	128	Common
            // 0 BMP	U+2580..U+259F	Block Elements	32	32	Common
            // 0 BMP	U+25A0..U+25FF	Geometric Shapes	96	96	Common
            // 0 BMP	U+2600..U+26FF	Miscellaneous Symbols	256	256	Common
            // 0 BMP	U+2700..U+27BF	Dingbats	192	192	Common
            { 0x2E80 , 0x2EFF , CODERANGE_CJK}, // CJK Radicals Supplement	128	115	Han
            { 0x2F00 , 0x2FDF , CODERANGE_CJK}, // Kangxi Radicals	224	214	Han
            { 0x2FF0 , 0x2FFF , CODERANGE_CJK}, // Ideographic Description Characters	16	12	Common
            { 0x3000 , 0x303F , CODERANGE_CJK}, // CJK Symbols and Punctuation	64	64	Han (15 characters), Hangul (2 characters), Common (43 characters), Inherited (4 characters)
            { 0x3040 , 0x309F , CODERANGE_CJK}, // Hiragana	96	93	Hiragana (89 characters), Common (2 characters), Inherited (2 characters)
            { 0x30A0 , 0x30FF , CODERANGE_CJK}, // Katakana	96	96	Katakana (93 characters), Common (3 characters)
            { 0x3100 , 0x312F , CODERANGE_CJK}, // Bopomofo	48	43	Bopomofo
            { 0x3130 , 0x318F , CODERANGE_CJK}, // Hangul Compatibility Jamo	96	94	Hangul
            { 0x3190 , 0x319F , CODERANGE_CJK}, // Kanbun	16	16	Common
            { 0x31A0 , 0x31BF , CODERANGE_CJK}, // Bopomofo Extended	32	32	Bopomofo
            { 0x31C0 , 0x31EF , CODERANGE_CJK}, // CJK Strokes	48	36	Common
            { 0x31F0 , 0x31FF , CODERANGE_CJK}, // Katakana Phonetic Extensions	16	16	Katakana
            { 0x3200 , 0x32FF , CODERANGE_CJK}, // Enclosed CJK Letters and Months	256	255	Hangul (62 characters), Katakana (47 characters), Common (146 characters)
            { 0x3300 , 0x33FF , CODERANGE_CJK}, // CJK Compatibility	256	256	Katakana (88 characters), Common (168 characters)
            { 0x3400 , 0x4DBF , CODERANGE_CJK}, // CJK Unified Ideographs Extension A	6,592	6,592	Han
            { 0x4DC0 , 0x4DFF , CODERANGE_CJK}, // Yijing Hexagram Symbols	64	64	Common
            { 0x4E00 , 0x9FFF , CODERANGE_CJK}, // CJK Unified Ideographs	20,992	20,989	Han
            { 0xF900 , 0xFAFF , CODERANGE_CJK}, // CJK Compatibility Ideographs	512	472	Han
            { 0xfffd , 0xfffe , CODERANGE_DEFAULT}
    };



    FileHandle freetypeFilehandle;
    StringBuilder codes;
    int referenceHeight = 1080;
    boolean scaleToReference = false;

    public DynamicFreeTypeFonter(FileHandle _fileHandle)
    {
        super();
        this.freetypeFilehandle = _fileHandle;
        this.codes = new StringBuilder();
        this.addCodeRanges(CODERANGE_DEFAULT);
    }

    Map<Integer, BitmapFont> sizedFonts = new HashMap<>();

    public BitmapFont getFont(int _size)
    {
        return this.getFont(_size, false);
    }
    public BitmapFont getFont(int _size, boolean _moreExact)
    {
        if(this.scaleToReference && (Gdx.graphics.getHeight() != this.referenceHeight))
        {

            _size = ((_size*Gdx.graphics.getHeight()*100)/this.referenceHeight)/100;
        }

        int _psize = normalizedSize(_size, _moreExact);

        if(this.sizedFonts.containsKey(_size))
        {
            return this.sizedFonts.get(_size);
        }


        FreeTypeFontGenerator.FreeTypeFontParameter _param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        _param.size = _psize;
        _param.characters=this.codes.toString();
        BitmapFont _font = new FreeTypeFontGenerator(this.freetypeFilehandle).generateFont(_param);
        if(!_moreExact && _size!=_psize)
        {
            _font.getData().setScale(((float)_size)/((float)_psize));
            _font.setUseIntegerPositions(true);
        }
        this.sizedFonts.put(_size, _font);
        return _font;
    }

    public void addCodeRanges(int... _CR)
    {
        for(int[] _cr : standardCodeRanges)
        {
            for(int _m : _CR)
            {
                if(_m==_cr[2])
                {
                    this.addCodeRange(_cr[0],_cr[1]);
                    break;
                }
            }
        }
    }

    public void addCode(int _code)
    {
        if(codes.lastIndexOf(Character.toString((char)_code))>=0) return;

        codes.append((char)_code);
    }

    public void addCodeRange(int _startCode, int _endCode)
    {
        for(int _i=_startCode; _i<=_endCode; _i++)
        {
            this.addCode(_i);
        }
    }

    public int getReferenceHeight() {
        return referenceHeight;
    }

    public void setReferenceHeight(int referenceHeight) {
        this.referenceHeight = referenceHeight;
    }

    public boolean isScaleToReference() {
        return scaleToReference;
    }

    public void setScaleToReference(boolean scaleToReference) {
        this.scaleToReference = scaleToReference;
    }
}
