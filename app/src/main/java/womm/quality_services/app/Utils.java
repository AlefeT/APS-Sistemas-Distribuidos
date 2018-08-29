package womm.quality_services.app;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

abstract class MaskEdittextUtil
{
    public static final String FORMAT_CPF = "###.###.###-##";
    public static final String FORMAT_TEL = "(##)####-####";
    public static final String FORMAT_CEL = "(##)#####-####";
    public static final String FORMAT_CEP = "#####-###";
    public static final String FORMAT_DATE = "##/##/####";
    public static final String FORMAT_HOUR = "##:##";

    /*****
     * Método que deve ser chamado para realizar a formatação
     *
     * @param ediTxt
     * @param mask
     * @return
     *
     * Use nos EditText da seguinte forma:  editTextCpf.addTextChangedListener(MaskEdittextUtil.mask(editTextCpf, MaskEdittextUtil.FORMAT_CPF));
     * Ou pode passar a formatação, como segue o exemplo: editTextCpf.addTextChangedListener(MaskEdittextUtil.mask(editTextCpf, "###.###.###-##"));
     *
     *****/

    public static TextWatcher mask(final EditText ediTxt, final String mask)
    {
        return new TextWatcher()
        {
            boolean isUpdating;
            String old = "";

            @Override
            public void afterTextChanged(final Editable s)
            {

            }

            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after)
            {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count)
            {
                final String str = MaskEdittextUtil.unmask(s.toString());
                String mascara = "";
                if (isUpdating)
                {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (final char m : mask.toCharArray())
                {
                    if (m != '#' && str.length() > old.length())
                    {
                        mascara += m;
                        continue;
                    }
                    try
                    {
                        mascara += str.charAt(i);
                    } catch (final Exception e)
                    {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                ediTxt.setText(mascara);
                ediTxt.setSelection(mascara.length());
            }
        };
    }

    public static String unmask(final String s)
    {
        return s.replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "").replaceAll("[(]", "").replaceAll("[ ]","").replaceAll("[:]", "").replaceAll("[)]", "");
    }
}

class RemoverAcentosUtil
{
    static String acentuado = "çÇáéíóúýÁÉÍÓÚÝàèìòùÀÈÌÒÙãõñäëïöüÿÄËÏÖÜÃÕÑâêîôûÂÊÎÔÛ";
    static String semAcento = "cCaeiouyAEIOUYaeiouAEIOUaonaeiouyAEIOUAONaeiouAEIOU";
    static char[] tabela;
    static
    {
        tabela = new char[256];
        for (int i = 0; i < tabela.length; ++i)
        {
            tabela [i] = (char) i;
        }
        for (int i = 0; i < acentuado.length(); ++i)
        {
            tabela [acentuado.charAt(i)] = semAcento.charAt(i);
        }
    }
    public static String remover (final String s)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); ++i)
        {
            char ch = s.charAt (i);
            if (ch < 256)
            {
                sb.append (tabela [ch]);
            } else {
                sb.append (ch);
            }
        }
        return sb.toString();
    }

}