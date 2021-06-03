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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.MessageFormat;

public class MakeFont {

    static int[][] _chars = {
            { 0x0020 , 0x007e ,0}, // Basic Latin, ASCII
            { 0x00a1 , 0x00ff ,0}, // Latin-1 Supplement
            { 0x02C6 , 0x02C7 ,0}, // Circumflex, Caron
            { 0x02D8 , 0x02DD ,0}, // Breve, Dot Above, Ring Above, Ogonek, Small Tilde, Double Acute Accent
            { 0x2010 , 0x205f ,0}, // General Punctuation
            { 0x20a4 , 0x20a4 ,0}, // Pound Sign
            { 0x20ac , 0x20ac ,0}, // Euro Sign
            { 0x20bf , 0x20bf ,0}, // BitCoin Sign
            { 0x2122 , 0x2122 ,0}, // (TM)
            { 0x2212 , 0x2212 ,0}, // minus
            { 0xfffd , 0xfffe ,0},
            { 0x0100 , 0x024f ,0}, // Latin Extended-A, Latin Extended-B
            { 0x0370 , 0x03ff ,0}, // Greek, Coptic
            { 0x0400 , 0x04ff ,0}, // cyrillic
            { 0x0500 , 0x052f ,0}, // cyrillic supplement
            { 0x1e00 , 0x1eff ,1}, // Latin Extended Additional
            { 0x1f00 , 0xfff0 ,0},
    };

    public static void main(String[] args) throws IOException, FontFormatException {

        File _ttf = new File("src/main/resources/canada1500-rg.otf");
        makeFont(_ttf,32,512,new File("."),"canada1500-rg-32", _chars);

    }

    public static void makeFont(File _ttf, int _size, int _xyres, File _outdir, String _prefix, int[][] _codesets) throws IOException, FontFormatException
    {
        Font _fnt = Font.createFont(Font.TRUETYPE_FONT, _ttf).deriveFont((float)_size);
        BufferedImage _im = null;
        Graphics2D _g = null;

        StringWriter _sw = new StringWriter();
        PrintWriter _pw = new PrintWriter(_sw);

        int _page = 0;
        int _count = 0;
        int _y = 0;
        int _x = 0;
        boolean _stop =  false;
        for(int[] _charr : _codesets)
            for(int _char=_charr[0]; _char<(_charr[1]+1); _char++)
            {
                if(_im==null)
                {
                    _im = new BufferedImage(_xyres, _xyres,BufferedImage.TYPE_INT_ARGB);
                    _g = (Graphics2D) _im.getGraphics();
                    _g.setRenderingHint(
                            RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

                    _g.setRenderingHint(
                            RenderingHints.KEY_ALPHA_INTERPOLATION,
                            RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

                    _g.setComposite(AlphaComposite.Clear);
                    _g.fillRect(0, 0, _xyres, _xyres);

                    _g.setComposite(AlphaComposite.Src);
                    _g.setColor(Color.WHITE);
                    _g.setFont(_fnt);
                    _y = 0;
                    _x = 0;
                }

                if(!_fnt.canDisplay(_char))
                {
                    _pw.println(MessageFormat.format("char id={0,number,######0} x={1} y={2} width=0 height=0 xoffset=0 yoffset=0 xadvance={3} page=0 chnl=0 ",_char, _x, _y, _size));
                    _count++;
                    continue;
                }

                if((_charr[2]==1) && !Character.isAlphabetic(_char))
                {
                    continue;
                }

                FontMetrics _fm = new Canvas().getFontMetrics(_fnt);

                String _s = Character.toString((char) _char);
                LineMetrics _lm = _fm.getLineMetrics(_s, _g);
                int _w = (int)_fm.charWidth(_char);
                int _h = (int)_lm.getHeight();
                int _d = (int)_lm.getDescent();

                _g.drawString(_s, _x, _h+(_y)-_d);

                _pw.println(MessageFormat.format("char id={0,number,######0} x={1} y={2} width={3} height={4} xoffset=0 yoffset={5} xadvance={6} page={7} chnl=0 ",_char, _x, _y, _w, _h, _d, _w+1, _page));

                _count++;

                _x+=_w+2;
                if(_x+_size > _xyres)
                {
                    _y+=_h+2;
                    _x=0;
                }

                if(_y+(_size*2) > _xyres)
                {
                    ImageIO.write(_im,"png", new File(_outdir,_prefix+"."+_page+".png"));
                    _page++;
                    _g.dispose();
                    _im=null;
                }
            }
        _pw.flush();
        _pw.close();

        _pw = new PrintWriter(new FileWriter(new File(_outdir,_prefix+".fnt")));
        _pw.println("info face=\"Sans\" size=16 bold=0 italic=0 charset=\"latin1\" unicode=0 stretchH=100 smooth=1 aa=1 padding=1,1,1,1 spacing=0,0");
        _pw.println("common lineHeight="+_size+" base="+_size+" scaleW="+_xyres+" scaleH="+_xyres+" pages="+(_page+1)+" packed=0");
        for(int _i=0; _i<_page+1; _i++)
        {
            _pw.println("page id="+_i+" file=\""+_prefix+"."+_i+".png\"");
        }
        _pw.println("chars count="+_count);
        _pw.println(_sw.getBuffer().toString());
        _pw.close();

        ImageIO.write(_im,"png", new File(_outdir,_prefix+"."+_page+".png"));
    }

}
