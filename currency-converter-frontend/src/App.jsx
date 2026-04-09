//React front basic usage
import { useState } from "react";

export default function CurrencyConverter() {
  const [from, setFrom] = useState("USD");
  const [to, setTo] = useState("GBP");
  const [amount, setAmount] = useState("");
  const [result, setResult] = useState(null);

  const handleConvert = async () => {
    const res = await fetch(
      `http://localhost:8080/api/convert?from=${from}&to=${to}&amount=${amount}`
    );
    const data = await res.json();
    setResult(data.result); // adjust based on actual API response shape
  };

  return (
    <div>
      <input value={from} onChange={e => setFrom(e.target.value)} placeholder="From (e.g. USD)" />
      <input value={to} onChange={e => setTo(e.target.value)} placeholder="To (e.g. GBP)" />
      <input value={amount} onChange={e => setAmount(e.target.value)} placeholder="Amount" />
      <button onClick={handleConvert}>Convert</button>
      {result && <p>Result: {result}</p>}
    </div>
  );
}
//hello