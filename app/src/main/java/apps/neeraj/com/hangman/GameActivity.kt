package apps.neeraj.com.hangman

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.AdapterView.OnItemSelectedListener
import java.io.InputStream
import java.util.*

class GameActivity : AppCompatActivity(), OnClickListener, OnItemSelectedListener{

    enum class Alphabets(val alpha:Char) {
        A('A'),
        B('B'),
        C('C'),
        D('D'),
        E('E'),
        F('F'),
        G('G'),
        H('H'),
        I('I'),
        J('J'),
        K('K'),
        L('L'),
        M('M'),
        N('N'),
        O('O'),
        P('P'),
        Q('Q'),
        R('R'),
        S('S'),
        T('T'),
        U('U'),
        V('V'),
        W('W'),
        X('X'),
        Y('Y'),
        Z('Z')
    }

    var letter: Char = '\u0000'
    private var wordList: MutableList<String> = mutableListOf<String>()
    private var missList: MutableList<Char> = mutableListOf<Char>()
    private var randomWord = ""
    private val random = Random()

    lateinit var txtView: TextView
    lateinit var dpStrTxtView: TextView
    lateinit var attemptsTxtView: TextView
    lateinit var misses: TextView
    lateinit var imgView: ImageView
    lateinit var iconImgView: ImageView
    lateinit var spinner: Spinner
    lateinit var inputStream: InputStream

    private var displayString = ""
    private var oldString = ""
    private val TAG = "HANGMAN"
    var newGame: Boolean = false
    var typeSelected: Boolean = false
    private val TOTAL_ATTEMPTS = 5
    var attempts: Int = TOTAL_ATTEMPTS


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        txtView = findViewById<TextView>(R.id.textView)
        dpStrTxtView = findViewById<TextView>(R.id.displayString)
        attemptsTxtView = findViewById<TextView>(R.id.attempts)
        misses = findViewById<TextView>(R.id.misses)
        misses.visibility = View.INVISIBLE

        imgView = findViewById<ImageView>(R.id.imageView)
        iconImgView = findViewById<ImageView>(R.id.hangmanIcon)
        spinner = findViewById<Spinner>(R.id.spinner)
        spinner.onItemSelectedListener = this;

        attemptsTxtView.text = "Attempts: " + attempts

        Log.d(TAG, "OnCreate")
    }

    override fun onClick(v:View) {
        Log.d(TAG, "OnClick")
        if (v != null) {
            when(v.id) {
                R.id.button1 -> {
                    this.letter = Alphabets.A.toString()[0]
                }
                R.id.button2 -> {
                    this.letter = Alphabets.B.toString()[0]
                }
                R.id.button3 -> {
                    this.letter = Alphabets.C.toString()[0]
                }
                R.id.button4 -> {
                    this.letter = Alphabets.D.toString()[0]
                }
                R.id.button5 -> {
                    this.letter = Alphabets.E.toString()[0]
                }
                R.id.button6 -> {
                    this.letter = Alphabets.F.toString()[0]
                }
                R.id.button7 -> {
                    this.letter = Alphabets.G.toString()[0]
                }
                R.id.button8 -> {
                    this.letter = Alphabets.H.toString()[0]
                }
                R.id.button9 -> {
                    this.letter = Alphabets.I.toString()[0]
                }
                R.id.button10 -> {
                    this.letter = Alphabets.J.toString()[0]
                }
                R.id.button11 -> {
                    this.letter = Alphabets.K.toString()[0]
                }
                R.id.button12 -> {
                    this.letter = Alphabets.L.toString()[0]
                }
                R.id.button13 -> {
                    this.letter = Alphabets.M.toString()[0]
                }
                R.id.button14 -> {
                    this.letter = Alphabets.N.toString()[0]
                }
                R.id.button15 -> {
                    this.letter = Alphabets.O.toString()[0]
                }
                R.id.button16 -> {
                    this.letter = Alphabets.P.toString()[0]
                }
                R.id.button17 -> {
                    this.letter = Alphabets.Q.toString()[0]
                }
                R.id.button18 -> {
                    this.letter = Alphabets.R.toString()[0]
                }
                R.id.button19 -> {
                    this.letter = Alphabets.S.toString()[0]
                }
                R.id.button20 -> {
                    this.letter = Alphabets.T.toString()[0]
                }
                R.id.button21 -> {
                    this.letter = Alphabets.U.toString()[0]
                }
                R.id.button22 -> {
                    this.letter = Alphabets.V.toString()[0]
                }
                R.id.button23 -> {
                    this.letter = Alphabets.W.toString()[0]
                }
                R.id.button24 -> {
                    this.letter = Alphabets.X.toString()[0]
                }
                R.id.button25 -> {
                    this.letter = Alphabets.Y.toString()[0]
                }
                R.id.button26 -> {
                    this.letter = Alphabets.Z.toString()[0]
                }
                R.id.buttonNewGame -> {
                    Log.d(TAG, "button New Game pressed!")

                    newGame = true
                    letter = '\u0000'
                    missList.clear()

                    attempts = TOTAL_ATTEMPTS
                    attemptsTxtView.text = "Attempts: " + attempts
                    attemptsTxtView.visibility = View.VISIBLE

                    imgView.setImageResource(R.drawable.hangman1)
                    imgView.visibility = View.VISIBLE

                    spinner.visibility = View.VISIBLE
                    if (typeSelected) {
                        this.selectWordAndSetDPString()
                    }
                    else {
                        dpStrTxtView.visibility = View.INVISIBLE
                    }
                    iconImgView.visibility = View.INVISIBLE

                    val txtViewHint = findViewById<TextView>(R.id.textView)
                    txtView.text = "Starting new Game!"
                    txtView.visibility = View.VISIBLE

                    misses.text = "Misses: "
                    misses.visibility = View.VISIBLE

                    Log.d(TAG, "" + !letter.equals('\u0000', false))
                }
            }
        }
        if (!letter.equals('\u0000', false) && newGame && typeSelected) {
            Log.d(TAG, "In if for letter comparison")
            oldString = displayString
            displayString = evaluateString(letter, randomWord, displayString)

            txtView.visibility = View.INVISIBLE

            if (oldString == displayString) {
                attempts--
                attemptsTxtView.text = "Attempts: " + attempts
                missList.add(letter)
                misses.text = "Misses: " + missList.toString()
                misses.visibility = View.VISIBLE

                when(attempts) {
                    0 -> {
                        imgView.setImageResource(R.drawable.hangman6)
                    }
                    1 -> {
                        imgView.setImageResource(R.drawable.hangman5)
                    }
                    2 -> {
                        imgView.setImageResource(R.drawable.hangman4)
                    }
                    3 -> {
                        imgView.setImageResource(R.drawable.hangman3)
                    }
                    4 -> {
                        imgView.setImageResource(R.drawable.hangman2)
                    }
                    5 -> {
                        imgView.setImageResource(R.drawable.hangman1)
                    }
                }

                dpStrTxtView.text = displayString
                Log.d(TAG, "old == display, $attempts")
            }
            else if (displayString == randomWord) {
                txtView.text = "Great Job!"
                txtView.visibility = View.VISIBLE
                dpStrTxtView.text = randomWord
                Log.d(TAG, "Great Job!")
                letter = '\u0000'
                attempts = TOTAL_ATTEMPTS
                newGame = false
                missList.clear()
            }
            else {
                dpStrTxtView.text = displayString
                txtView.text = "Good going!"
                txtView.visibility = View.VISIBLE
            }
            imgView.visibility = View.VISIBLE
            if (attempts == 0) {
                newGame = false
                txtView.text = "Hard Luck"
                txtView.visibility = View.VISIBLE
                dpStrTxtView.text = randomWord
                Log.d(TAG, "attempts == 0, $displayString")
                letter = '\u0000'
                attempts = TOTAL_ATTEMPTS
                missList.clear()

            }
        }
        else {
            letter = '\u0000'
            Log.d(TAG, "new game false")
            Log.d(TAG, "In else: $letter, " + !letter.equals('\u0000', false))
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.d(TAG, "OnItemSelected")
        if (p0 != null) {
            var type = p0.getItemAtPosition(p2).toString()
            Log.d(TAG, "OnItemSelected: $type")
            var fileForType = "";
            if (type.contains(' ')) {
                Log.d(TAG, "got $type")
            }
            else {
                when(type) {
                    "Animals" -> {
                        fileForType = "basic/animals.txt"
                    }
                    "Bank" -> {
                        fileForType = "basic/bank.txt"
                    }
                    "Baseball" -> {
                        fileForType = "basic/baseball.txt"
                    }
                    "Body" -> {
                        fileForType = "basic/body.txt"
                    }
                    "Buildings-Places" -> {
                        fileForType = "basic/buildings_places.txt"
                    }
                    "Classroom" -> {
                        fileForType = "basic/classroom.txt"
                    }
                    "Clothes" -> {
                        fileForType = "basic/clothes.txt"
                    }
                    "Colors" -> {
                        fileForType = "basic/colors.txt"
                    }
                    "Cooking" -> {
                        fileForType = "basic/cooking.txt"
                    }
                    "Days" -> {
                        fileForType = "basic/days.txt"
                    }
                    "Dinner-Table" -> {
                        fileForType = "basic/dinnertable.txt"
                    }
                    "Fruits" -> {
                        fileForType = "basic/fruits.txt"
                    }
                    "Geography" -> {
                        fileForType = "basic/geography.txt"
                    }
                    "House" -> {
                        fileForType = "basic/house.txt"
                    }
                    "Months" -> {
                        fileForType = "basic/months.txt"
                    }
                    "Restaurant" -> {
                        fileForType = "basic/restaurant.txt"
                    }
                    "Tools" -> {
                        fileForType = "basic/tools.txt"
                    }
                    "Transportation" -> {
                        fileForType = "basic/transportation.txt"
                    }
                    "Twenty-Numbers" -> {
                        fileForType = "basic/twentynumbers.txt"
                    }
                    "Vegetables" -> {
                        fileForType = "basic/vegetables.txt"
                    }
                    "Weather" -> {
                        fileForType = "basic/weather.txt"
                    }
                }

                Log.d(TAG, fileForType)
                newGame = true
                typeSelected = true
                inputStream = assets.open(fileForType)
                wordList = inputStream.bufferedReader().readLines() as MutableList<String>

                this.selectWordAndSetDPString()

                ///
                newGame = true
                letter = '\u0000'
                missList.clear()

                attempts = TOTAL_ATTEMPTS
                attemptsTxtView.text = "Attempts: " + attempts
                attemptsTxtView.visibility = View.VISIBLE

                imgView.setImageResource(R.drawable.hangman1)
                imgView.visibility = View.VISIBLE

                spinner.visibility = View.VISIBLE

                iconImgView.visibility = View.INVISIBLE

                val txtViewHint = findViewById<TextView>(R.id.textView)
                txtView.text = "Starting new Game!"
                txtView.visibility = View.VISIBLE

                misses.text = "Misses: "
                misses.visibility = View.VISIBLE
            }

        }
    }

    private fun selectWordAndSetDPString() {
        val size = wordList.size
        val randomNum = randomFn(0, size - 1)
        Log.d(TAG, "" + randomNum + ", " + this.wordList[randomNum])

        randomWord = wordList[randomNum].toUpperCase()
        val length = randomWord.length
        displayString = "_".repeat(length)
        dpStrTxtView.text = displayString
        dpStrTxtView.visibility = View.VISIBLE
        Log.d(TAG, displayString)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    private fun randomFn(from: Int, to: Int) : Int {
        return random.nextInt(to - from) + from
    }

    private fun evaluateString(letter: Char, word: String, dpString: String): String {
        var evalString = ""
        val length = word.length
        var i = 0
        Log.d(TAG, "$letter, $word, $dpString")
        if (word.contains(letter)) {
            Log.d(TAG, "word contains letter")
            while (i < length) {
//                Log.d(TAG, "$evalString" + i )
                if (word[i] == letter) {
                    evalString += letter
                }
                else {
                    evalString += dpString[i]
                }
                i++
            }
            Log.d(TAG, "$letter, $word, $dpString, $evalString")
            return evalString
        }
        else return dpString
    }

}
