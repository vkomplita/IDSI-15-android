package zxing.library;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.*;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * Created by rusfearuth on 2/21/14.
 */
public class ZXingUtils
{
    public static void generateAndSet(final BarcodeFormat aFormat, final ImageView aView, final String aBarCode)
    {
        Bitmap barCodeImage = null;
        Writer codeWriter = null;
        ViewGroup.LayoutParams params = aView.getLayoutParams();

        switch (aFormat)
        {
            case QR_CODE:
                codeWriter = new QRCodeWriter();
                break;
            case CODE_128:
                codeWriter = new Code128Writer();
                break;
            case CODE_39:
                codeWriter = new Code39Writer();
                break;
            case PDF_417:
                codeWriter = new PDF417Writer();
                break;
            case EAN_8:
                codeWriter = new EAN8Writer();
                break;
            case EAN_13:
                codeWriter = new EAN13Writer();
                break;
            case UPC_A:
                codeWriter = new UPCAWriter();
                break;
        }

        if (codeWriter != null)
        {
            try
            {
                BitMatrix bm = codeWriter.encode(aBarCode, aFormat, params.width, params.height);
                barCodeImage = Bitmap.createBitmap(params.width, params.height, Bitmap.Config.ARGB_8888);

                for (int i = 0; i < params.width; i++)
                {
                    for (int j = 0; j < params.height; j++)
                    {

                        barCodeImage.setPixel(i, j, bm.get(i, j) ? Color.BLACK : Color.WHITE);
                    }
                }
            } catch (WriterException e)
            {
                e.printStackTrace();
            }
            if (barCodeImage != null)
            {
                aView.setImageBitmap(barCodeImage);
            }
        }
    }

    public static void generateAndSetAsync(final BarcodeFormat aFormat, final ImageView aView, final String aBarCode)
    {
        final ViewGroup.LayoutParams viewParams = aView.getLayoutParams();

        new AsyncTask<Void, Void, Bitmap>()
        {

            @Override
            protected Bitmap doInBackground(Void... params)
            {
                com.google.zxing.Writer codeWriter = null;
                switch (aFormat)
                {
                    case QR_CODE:
                        codeWriter = new QRCodeWriter();
                        break;
                    case CODE_128:
                        codeWriter = new Code128Writer();
                        break;
                    case CODE_39:
                        codeWriter = new Code39Writer();
                        break;
                    case PDF_417:
                        codeWriter = new PDF417Writer();
                        break;
                    case EAN_8:
                        codeWriter = new EAN8Writer();
                        break;
                    case EAN_13:
                        codeWriter = new EAN13Writer();
                        break;
                    case UPC_A:
                        codeWriter = new UPCAWriter();
                        break;
                }
                Bitmap barCodeImage = null;
                if (codeWriter != null)
                {

                    try
                    {
                        BitMatrix bm = codeWriter.encode(aBarCode, aFormat, viewParams.width, viewParams.height);
                        barCodeImage = Bitmap.createBitmap(viewParams.width, viewParams.height, Bitmap.Config.ARGB_8888);

                        for (int i = 0; i < viewParams.width; i++)
                        {
                            for (int j = 0; j < viewParams.height; j++)
                            {

                                barCodeImage.setPixel(i, j, bm.get(i, j) ? Color.BLACK : Color.WHITE);
                            }
                        }
                    } catch (WriterException e)
                    {
                        e.printStackTrace();
                    }
                }
                return barCodeImage;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap)
            {
                if (bitmap != null)
                {
                    aView.setImageBitmap(bitmap);
                }
            }
        }.execute();
    }
}
