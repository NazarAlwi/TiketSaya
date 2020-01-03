package firstbelajar.digitalsoftware.tiketsaya;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolde> {
    private Context mContext;
    private ArrayList<MyTicket> mMyTickets;

    public TicketAdapter(Context context, ArrayList<MyTicket> myTickets) {
        this.mContext = context;
        this.mMyTickets = myTickets;
    }

    @NonNull
    @Override
    public TicketViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_my_profile, parent, false);
        return new TicketViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TicketViewHolde holder, int position) {
        MyTicket myTicket = mMyTickets.get(position);

        holder.tvNamaWisata.setText(myTicket.getNama_wisata());
        holder.tvLokasiWisata.setText(myTicket.getLokasi());
        holder.tvJumlahTiket.setText(String.format(mContext.getResources().getString(R.string.tickets), myTicket.getJumlah_tiket()));

        final String namaWisata = myTicket.getNama_wisata();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToTicketDetail = new Intent(mContext, MyProfileTicketDetailActivity.class);
                goToTicketDetail.putExtra("nama_wisata", namaWisata);
                mContext.startActivity(goToTicketDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMyTickets.size();
    }

    public class TicketViewHolde extends RecyclerView.ViewHolder {
        TextView tvNamaWisata;
        TextView tvLokasiWisata;
        TextView tvJumlahTiket;

        public TicketViewHolde(@NonNull View itemView) {
            super(itemView);
            tvNamaWisata = itemView.findViewById(R.id.tv_nama_wisata_profile);
            tvLokasiWisata = itemView.findViewById(R.id.tv_lokasi_wisata_profile);
            tvJumlahTiket = itemView.findViewById(R.id.tv_jumlah_ticket);
        }
    }
}
