from flask import Flask
py_app = Flask(__name__)
@py_app.route('/')
def python_flask();
  return """
  <h1 style="text-align:center;">Python & Flask Web</h1>
  <p style="text-align:center;>This is micro web framework for running Flask inside Docker</p>
  """

if __name__=='__main__':
  py_app.run(host='0.0.0.0', port=9000, debug=True)