package carballo.rafael.clicker

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    var cuenta:Int =0
    lateinit var tvcontador :TextView
    lateinit var et_que:EditText
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_sum :ImageButton=findViewById(R.id.btn_add)
        val btn_del :ImageButton=findViewById(R.id.btn_del)
        val btn_sus :ImageButton=findViewById(R.id.btn_sus)
        et_que=findViewById(R.id.et_que)
        tvcontador=findViewById(R.id.contador)


        btn_sum.setOnClickListener(){
            cuenta++
            tvcontador.setText("$cuenta")
        }
        btn_sus.setOnClickListener(){
            cuenta--
            tvcontador.setText("$cuenta")
        }
        btn_del.setOnClickListener(){
            val  alertDialog:AlertDialog=this.let {
                val builder=AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton("borrar",DialogInterface.OnClickListener{
                        dialog,id->cuenta=0
                        tvcontador.setText("$cuenta")
                    })
                    setNegativeButton("Cancelar",DialogInterface.OnClickListener{
                        dialog,id->//user cancelled the dialog
                    })

                }
                //set other dialog properties
                    builder.setMessage("Seguro que desea borrar la cuenta?").setTitle("Cuidado!")
                //create the AlertDialog
                builder.create()

            }
            alertDialog.show()
        }

    }

    override fun onPause() {
        super.onPause()
        val sharedpref=this.getPreferences(Context.MODE_PRIVATE) ?:return
        with(sharedpref.edit()){
            putInt("contador",cuenta)
            putString("que",et_que.text.toString())
            commit()
        }

    }

    override fun onResume() {
        super.onResume()
        val sharedpref=this.getPreferences(Context.MODE_PRIVATE)?:return
        cuenta=sharedpref.getInt("contador",0)
        tvcontador.setText(cuenta)
        et_que.setText(sharedpref.getString("que","clicks"))
    }


}