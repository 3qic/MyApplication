package com.example.myapplication;

import com.example.myapplication.Constant;
import com.example.myapplication.R;

public class ThemeChooser {

        public void setColorTheme(){
            switch(Constant.color){
                case 0xFF1CCA33:
                    Constant.theme = R.style.AppThemeGreen;
                    break;

                case 0xFF13ABC2:
                    Constant.theme = R.style.AppThemeBlue;
                    break;

                case 0xFFE6CD38:
                    Constant.theme = R.style.AppThemeYellow;
                    break;

                case 0xFF3D3939:
                    Constant.theme = R.style.AppThemeBlack;
                    break;

                case 0xFFE72A2A:
                    Constant.theme = R.style.AppThemeRed;
                    break;


            }

    }

}
