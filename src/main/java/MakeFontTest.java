/*
 * Copyright (c) 2021. terefang@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import lombok.SneakyThrows;

import java.awt.*;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;

public class MakeFontTest
{
    static int[][] _chars = {
            { 0x0020 , 0x007e ,0}, // Basic Latin, ASCII
            { 0x00a1 , 0x00ff ,0}, // Latin-1 Supplement
            { 0x0100 , 0x017f ,0}, // Latin Extended-A
            { 0x0180 , 0x024f ,0}, // Latin Extended-B
            { 0x02C6 , 0x02C7 ,0}, // Circumflex, Caron
            { 0x02D8 , 0x02DD ,0}, // Breve, Dot Above, Ring Above, Ogonek, Small Tilde, Double Acute Accent
            { 0x0370 , 0x03ff ,0}, // Greek, Coptic
            { 0x0400 , 0x04ff ,0}, // cyrillic
            { 0x0500 , 0x052f ,0}, // cyrillic supplement
            { 0x1e00 , 0x1eff ,1}, // Latin Extended Additional
            { 0x2010 , 0x205f ,0}, // General Punctuation
            { 0x20a0 , 0x20bf ,0}, // Currency Symbols -- Pound Sign, Euro Sign, BitCoin Sign
            { 0x2122 , 0x2122 ,0}, // (TM)
            //{ 0x2100 , 0x21FF ,0}, // Arrows
            { 0x2212 , 0x2212 ,0}, // minus
            { 0xfffd , 0xfffe ,0},
    };

    @SneakyThrows
    public static void main(String[] args) throws IOException, FontFormatException {

        File _ttf = new File("/data/fredo/_fontz/_pub/Lato/Lato-Regular.ttf");
        int _xy = 4096;
        int _fs = 128;
        makeFont(true, _ttf,_fs,_xy,new File("."),"lato-16-awt", _chars, -1f);
        makeFont(false, _ttf,_fs,_xy,new File("."),"lato-16-gft", _chars, -1f);

        makeFont(true, _ttf,_fs,_xy,new File("."),"lato-16-awt-gc", _chars, .5f);
        makeFont(false, _ttf,_fs,_xy,new File("."),"lato-16-gft-gc", _chars, .5f);

    }

    @SneakyThrows
    public static void makeFont(boolean _awtgdx, File _ttf, int _size, int _xyres, File _outdir, String _prefix, int[][] _codesets, float _gamma) {
        IndexColorModel _icm = null;
        byte[] _bw = {0, (byte) 255};
        _icm = new IndexColorModel(1, _bw.length,_bw,_bw,_bw,_bw);

        //byte[] _bw = {0, (byte) 48, (byte) 96,  (byte) (96+48), (byte) 192, (byte) (192+32),  (byte) (192+64), (byte) 255};
        // _icm = new IndexColorModel(4, _bw.length,_bw,_bw,_bw,_bw);


        //byte[] _bw = {0, (byte) 96, (byte) 192, (byte) 255};
        //_icm = new IndexColorModel(2, _bw.length,_bw,_bw,_bw,_bw);

        /*
        int _n = 256;
        byte[] _bw = new byte[_n];
        for (int _i = 0; _i < _n; _i++) _bw[_i] = (byte) (255f * ((float) _i / (float) (_n-1)));
         _icm = new IndexColorModel(8, _bw.length, _bw, _bw, _bw, _bw);
        */

        if(_awtgdx)
        {
            MakeFontAWT.makeFont(_ttf, _size,_xyres,_outdir,_prefix,_codesets, _icm, _gamma);
        }
        else
        {
            MakeFontGdxFt.makeFont(_ttf, _size,_xyres,_outdir,_prefix,_codesets, _icm, _gamma);
        }
    }
}

/*

Plane	Block range	Block name	Code points[a]	Assigned characters	Scripts[b][c][d][e][f]
 0 BMP	U+0000..U+007F	Basic Latin[g]	128	128	Latin (52 characters), Common (76 characters)
 0 BMP	U+0080..U+00FF	Latin-1 Supplement[h]	128	128	Latin (64 characters), Common (64 characters)
 0 BMP	U+0100..U+017F	Latin Extended-A	128	128	Latin
 0 BMP	U+0180..U+024F	Latin Extended-B	208	208	Latin
 0 BMP	U+0250..U+02AF	IPA Extensions	96	96	Latin
 0 BMP	U+02B0..U+02FF	Spacing Modifier Letters	80	80	Bopomofo (2 characters), Latin (14 characters), Common (64 characters)
 0 BMP	U+0300..U+036F	Combining Diacritical Marks	112	112	Inherited
 0 BMP	U+0370..U+03FF	Greek and Coptic	144	135	Coptic (14 characters), Greek (117 characters), Common (4 characters)
 0 BMP	U+0400..U+04FF	Cyrillic	256	256	Cyrillic (254 characters), Inherited (2 characters)
 0 BMP	U+0500..U+052F	Cyrillic Supplement	48	48	Cyrillic
 0 BMP	U+0530..U+058F	Armenian	96	91	Armenian
 0 BMP	U+0590..U+05FF	Hebrew	112	88	Hebrew
 0 BMP	U+0600..U+06FF	Arabic	256	255	Arabic (237 characters), Common (6 characters), Inherited (12 characters)
 0 BMP	U+0700..U+074F	Syriac	80	77	Syriac
 0 BMP	U+0750..U+077F	Arabic Supplement	48	48	Arabic
 0 BMP	U+0780..U+07BF	Thaana	64	50	Thaana
 0 BMP	U+07C0..U+07FF	NKo	64	62	Nko
 0 BMP	U+0800..U+083F	Samaritan	64	61	Samaritan
 0 BMP	U+0840..U+085F	Mandaic	32	29	Mandaic
 0 BMP	U+0860..U+086F	Syriac Supplement	16	11	Syriac
 0 BMP	U+08A0..U+08FF	Arabic Extended-A	96	84	Arabic (83 characters), Common (1 character)
 0 BMP	U+0900..U+097F	Devanagari	128	128	Devanagari (122 characters), Common (2 characters), Inherited (4 characters)
 0 BMP	U+0980..U+09FF	Bengali	128	96	Bengali
 0 BMP	U+0A00..U+0A7F	Gurmukhi	128	80	Gurmukhi
 0 BMP	U+0A80..U+0AFF	Gujarati	128	91	Gujarati
 0 BMP	U+0B00..U+0B7F	Oriya	128	91	Oriya
 0 BMP	U+0B80..U+0BFF	Tamil	128	72	Tamil
 0 BMP	U+0C00..U+0C7F	Telugu	128	98	Telugu
 0 BMP	U+0C80..U+0CFF	Kannada	128	89	Kannada
 0 BMP	U+0D00..U+0D7F	Malayalam	128	118	Malayalam
 0 BMP	U+0D80..U+0DFF	Sinhala	128	91	Sinhala
 0 BMP	U+0E00..U+0E7F	Thai	128	87	Thai (86 characters), Common (1 character)
 0 BMP	U+0E80..U+0EFF	Lao	128	82	Lao
 0 BMP	U+0F00..U+0FFF	Tibetan	256	211	Tibetan (207 characters), Common (4 characters)
 0 BMP	U+1000..U+109F	Myanmar	160	160	Myanmar
 0 BMP	U+10A0..U+10FF	Georgian	96	88	Georgian (87 characters), Common (1 character)
 0 BMP	U+1100..U+11FF	Hangul Jamo	256	256	Hangul
 0 BMP	U+1200..U+137F	Ethiopic	384	358	Ethiopic
 0 BMP	U+1380..U+139F	Ethiopic Supplement	32	26	Ethiopic
 0 BMP	U+13A0..U+13FF	Cherokee	96	92	Cherokee
 0 BMP	U+1400..U+167F	Unified Canadian Aboriginal Syllabics	640	640	Canadian Aboriginal
 0 BMP	U+1680..U+169F	Ogham	32	29	Ogham
 0 BMP	U+16A0..U+16FF	Runic	96	89	Runic (86 characters), Common (3 characters)
 0 BMP	U+1700..U+171F	Tagalog	32	20	Tagalog
 0 BMP	U+1720..U+173F	Hanunoo	32	23	Hanunoo (21 characters), Common (2 characters)
 0 BMP	U+1740..U+175F	Buhid	32	20	Buhid
 0 BMP	U+1760..U+177F	Tagbanwa	32	18	Tagbanwa
 0 BMP	U+1780..U+17FF	Khmer	128	114	Khmer
 0 BMP	U+1800..U+18AF	Mongolian	176	157	Mongolian (154 characters), Common (3 characters)
 0 BMP	U+18B0..U+18FF	Unified Canadian Aboriginal Syllabics Extended	80	70	Canadian Aboriginal
 0 BMP	U+1900..U+194F	Limbu	80	68	Limbu
 0 BMP	U+1950..U+197F	Tai Le	48	35	Tai Le
 0 BMP	U+1980..U+19DF	New Tai Lue	96	83	New Tai Lue
 0 BMP	U+19E0..U+19FF	Khmer Symbols	32	32	Khmer
 0 BMP	U+1A00..U+1A1F	Buginese	32	30	Buginese
 0 BMP	U+1A20..U+1AAF	Tai Tham	144	127	Tai Tham
 0 BMP	U+1AB0..U+1AFF	Combining Diacritical Marks Extended	80	17	Inherited
 0 BMP	U+1B00..U+1B7F	Balinese	128	121	Balinese
 0 BMP	U+1B80..U+1BBF	Sundanese	64	64	Sundanese
 0 BMP	U+1BC0..U+1BFF	Batak	64	56	Batak
 0 BMP	U+1C00..U+1C4F	Lepcha	80	74	Lepcha
 0 BMP	U+1C50..U+1C7F	Ol Chiki	48	48	Ol Chiki
 0 BMP	U+1C80..U+1C8F	Cyrillic Extended-C	16	9	Cyrillic
 0 BMP	U+1C90..U+1CBF	Georgian Extended	48	46	Georgian
 0 BMP	U+1CC0..U+1CCF	Sundanese Supplement	16	8	Sundanese
 0 BMP	U+1CD0..U+1CFF	Vedic Extensions	48	43	Common (16 characters), Inherited (27 characters)
 0 BMP	U+1D00..U+1D7F	Phonetic Extensions	128	128	Cyrillic (2 characters), Greek (15 characters), Latin (111 characters)
 0 BMP	U+1D80..U+1DBF	Phonetic Extensions Supplement	64	64	Greek (1 character), Latin (63 characters)
 0 BMP	U+1DC0..U+1DFF	Combining Diacritical Marks Supplement	64	63	Inherited
 0 BMP	U+1E00..U+1EFF	Latin Extended Additional	256	256	Latin
 0 BMP	U+1F00..U+1FFF	Greek Extended	256	233	Greek
 0 BMP	U+2000..U+206F	General Punctuation	112	111	Common (109 characters), Inherited (2 characters)
 0 BMP	U+2070..U+209F	Superscripts and Subscripts	48	42	Latin (15 characters), Common (27 characters)
 0 BMP	U+20A0..U+20CF	Currency Symbols	48	32	Common
 0 BMP	U+20D0..U+20FF	Combining Diacritical Marks for Symbols	48	33	Inherited
 0 BMP	U+2100..U+214F	Letterlike Symbols	80	80	Greek (1 character), Latin (4 characters), Common (75 characters)
 0 BMP	U+2150..U+218F	Number Forms	64	60	Latin (41 characters), Common (19 characters)
 0 BMP	U+2190..U+21FF	Arrows	112	112	Common
 0 BMP	U+2200..U+22FF	Mathematical Operators	256	256	Common
 0 BMP	U+2300..U+23FF	Miscellaneous Technical	256	256	Common
 0 BMP	U+2400..U+243F	Control Pictures	64	39	Common
 0 BMP	U+2440..U+245F	Optical Character Recognition	32	11	Common
 0 BMP	U+2460..U+24FF	Enclosed Alphanumerics	160	160	Common
 0 BMP	U+2500..U+257F	Box Drawing	128	128	Common
 0 BMP	U+2580..U+259F	Block Elements	32	32	Common
 0 BMP	U+25A0..U+25FF	Geometric Shapes	96	96	Common
 0 BMP	U+2600..U+26FF	Miscellaneous Symbols	256	256	Common
 0 BMP	U+2700..U+27BF	Dingbats	192	192	Common
 0 BMP	U+27C0..U+27EF	Miscellaneous Mathematical Symbols-A	48	48	Common
 0 BMP	U+27F0..U+27FF	Supplemental Arrows-A	16	16	Common
 0 BMP	U+2800..U+28FF	Braille Patterns	256	256	Braille
 0 BMP	U+2900..U+297F	Supplemental Arrows-B	128	128	Common
 0 BMP	U+2980..U+29FF	Miscellaneous Mathematical Symbols-B	128	128	Common
 0 BMP	U+2A00..U+2AFF	Supplemental Mathematical Operators	256	256	Common
 0 BMP	U+2B00..U+2BFF	Miscellaneous Symbols and Arrows	256	253	Common
 0 BMP	U+2C00..U+2C5F	Glagolitic	96	94	Glagolitic
 0 BMP	U+2C60..U+2C7F	Latin Extended-C	32	32	Latin
 0 BMP	U+2C80..U+2CFF	Coptic	128	123	Coptic
 0 BMP	U+2D00..U+2D2F	Georgian Supplement	48	40	Georgian
 0 BMP	U+2D30..U+2D7F	Tifinagh	80	59	Tifinagh
 0 BMP	U+2D80..U+2DDF	Ethiopic Extended	96	79	Ethiopic
 0 BMP	U+2DE0..U+2DFF	Cyrillic Extended-A	32	32	Cyrillic
 0 BMP	U+2E00..U+2E7F	Supplemental Punctuation	128	83	Common


 0 BMP	U+A000..U+A48F	Yi Syllables	1,168	1,165	Yi
 0 BMP	U+A490..U+A4CF	Yi Radicals	64	55	Yi
 0 BMP	U+A4D0..U+A4FF	Lisu	48	48	Lisu
 0 BMP	U+A500..U+A63F	Vai	320	300	Vai
 0 BMP	U+A640..U+A69F	Cyrillic Extended-B	96	96	Cyrillic
 0 BMP	U+A6A0..U+A6FF	Bamum	96	88	Bamum
 0 BMP	U+A700..U+A71F	Modifier Tone Letters	32	32	Common
 0 BMP	U+A720..U+A7FF	Latin Extended-D	224	180	Latin (175 characters), Common (5 characters)
 0 BMP	U+A800..U+A82F	Syloti Nagri	48	45	Syloti Nagri
 0 BMP	U+A830..U+A83F	Common Indic Number Forms	16	10	Common
 0 BMP	U+A840..U+A87F	Phags-pa	64	56	Phags Pa
 0 BMP	U+A880..U+A8DF	Saurashtra	96	82	Saurashtra
 0 BMP	U+A8E0..U+A8FF	Devanagari Extended	32	32	Devanagari
 0 BMP	U+A900..U+A92F	Kayah Li	48	48	Kayah Li (47 characters), Common (1 character)
 0 BMP	U+A930..U+A95F	Rejang	48	37	Rejang
 0 BMP	U+A960..U+A97F	Hangul Jamo Extended-A	32	29	Hangul
 0 BMP	U+A980..U+A9DF	Javanese	96	91	Javanese (90 characters), Common (1 character)
 0 BMP	U+A9E0..U+A9FF	Myanmar Extended-B	32	31	Myanmar
 0 BMP	U+AA00..U+AA5F	Cham	96	83	Cham
 0 BMP	U+AA60..U+AA7F	Myanmar Extended-A	32	32	Myanmar
 0 BMP	U+AA80..U+AADF	Tai Viet	96	72	Tai Viet
 0 BMP	U+AAE0..U+AAFF	Meetei Mayek Extensions	32	23	Meetei Mayek
 0 BMP	U+AB00..U+AB2F	Ethiopic Extended-A	48	32	Ethiopic
 0 BMP	U+AB30..U+AB6F	Latin Extended-E	64	60	Latin (56 characters), Greek (1 character), Common (3 characters)
 0 BMP	U+AB70..U+ABBF	Cherokee Supplement	80	80	Cherokee
 0 BMP	U+ABC0..U+ABFF	Meetei Mayek	64	56	Meetei Mayek
 0 BMP	U+AC00..U+D7AF	Hangul Syllables	11,184	11,172	Hangul
 0 BMP	U+D7B0..U+D7FF	Hangul Jamo Extended-B	80	72	Hangul
 0 BMP	U+D800..U+DB7F	High Surrogates	896	0	Unknown
 0 BMP	U+DB80..U+DBFF	High Private Use Surrogates	128	0	Unknown
 0 BMP	U+DC00..U+DFFF	Low Surrogates	1,024	0	Unknown
 0 BMP	U+E000..U+F8FF	Private Use Area	6,400	6,400	Unknown

 0 BMP	U+FB00..U+FB4F	Alphabetic Presentation Forms	80	58	Armenian (5 characters), Hebrew (46 characters), Latin (7 characters)
 0 BMP	U+FB50..U+FDFF	Arabic Presentation Forms-A	688	611	Arabic (609 characters), Common (2 characters)
 0 BMP	U+FE00..U+FE0F	Variation Selectors	16	16	Inherited
 0 BMP	U+FE10..U+FE1F	Vertical Forms	16	10	Common
 0 BMP	U+FE20..U+FE2F	Combining Half Marks	16	16	Cyrillic (2 characters), Inherited (14 characters)
 0 BMP	U+FE30..U+FE4F	CJK Compatibility Forms	32	32	Common
 0 BMP	U+FE50..U+FE6F	Small Form Variants	32	26	Common
 0 BMP	U+FE70..U+FEFF	Arabic Presentation Forms-B	144	141	Arabic (140 characters), Common (1 character)
 0 BMP	U+FF00..U+FFEF	Halfwidth and Fullwidth Forms	240	225	Hangul (52 characters), Katakana (55 characters), Latin (52 characters), Common (66 characters)
 0 BMP	U+FFF0..U+FFFF	Specials	16	5	Common
*/