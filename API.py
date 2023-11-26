from googletrans import Translator
from flask import Flask, render_template, request, json, session, jsonify
import requests
 
translator = Translator()


app = Flask(__name__)

@app.route('/entovi', methods = ['POST'])
def entovi():
    en = request.form['en']
    vi = translator.translate(en,src='en',dest='vi')
    return vi.text

@app.route('/vitoen', methods = ['POST'])
def vitoen():
    vi = request.form['vi']
    en = translator.translate(vi,src='vi',dest='en')
    return en.text

if __name__ == '__main__':
    app.run()
